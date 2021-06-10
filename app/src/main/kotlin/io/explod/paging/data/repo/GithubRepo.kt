package io.explod.paging.data.repo

import androidx.annotation.VisibleForTesting
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dagger.Reusable
import io.explod.paging.data.Coro
import io.explod.paging.data.api.ApiExecutor
import io.explod.paging.data.api.GithubApi
import io.explod.paging.data.db.AppDatabase
import io.explod.paging.data.model.Either
import io.explod.paging.data.model.GithubUserResponse
import io.explod.paging.data.model.User
import io.explod.paging.data.model.UserPageKey
import io.explod.paging.util.readLink
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@Reusable
class GithubRepo @Inject constructor(
    private val coro: Coro,
    private val githubApiExecutor: ApiExecutor<GithubApi>,
    private val db: AppDatabase,
) {

    fun getPagedUsers(): Flow<PagingData<User>> {
        val pager = Pager(
            config = PagingConfig(userPageSize),
            remoteMediator = GithubUserPagingMediator(db, githubApiExecutor),
            pagingSourceFactory = db.users().pagedUsers().asPagingSourceFactory(
                fetchDispatcher = coro.db,
            ),
        )
        return pager.flow
    }

    class GithubUserPagingMediator(
        private val db: AppDatabase,
        private val githubApiExecutor: ApiExecutor<GithubApi>,
    ) : RemoteMediator<Int, User>() {

        private val users = db.users()
        private val userPageKeys = db.userPageKeys()

        override suspend fun initialize(): InitializeAction {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, User>,
        ): MediatorResult {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> db.withTransaction {
                    userPageKeys.maybeGetKey()
                }?.key ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            return when (val result =
                githubApiExecutor.call { getUsersPage(pageKey ?: defaultPageKey) }) {
                is Either.First -> saveResponse(result.value)
                is Either.Second -> MediatorResult.Error(result.value.exception)
            }
        }

        private suspend fun saveResponse(
            response: Response<List<GithubUserResponse>>,
        ): MediatorResult {
            val userResults = response.body() ?: emptyList()
            val nextKey = response.readLink(linkNext)
            db.withTransaction {
                userPageKeys.insertOrReplace(UserPageKey(key = nextKey))
                users.insertOrReplace(userResults.map {
                    User(id = it.id, username = it.username, avatarUrl = it.avatarUrl)
                })
            }
            return MediatorResult.Success(endOfPaginationReached = nextKey == null)
        }
    }

    companion object {
        private const val userPageSize = 20

        @VisibleForTesting
        internal const val defaultPageKey = "users?per_page=$userPageSize"
        private const val linkNext = "next"
    }
}