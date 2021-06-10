package io.explod.paging.ui.main

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import io.explod.paging.R
import io.explod.paging.data.Coro
import io.explod.paging.data.model.User
import io.explod.paging.data.repo.GithubRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.InvalidClassException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class MainActivityViewModel(
    private val githubRepository: GithubRepo,
    private val coro: Coro,
) : ViewModel() {

    private val loading = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = loading

    private val _errorMessage = MutableLiveData<ErrorMessage>()
    val errorMessage: LiveData<ErrorMessage>
        get() = _errorMessage
    private val _showErrorMessage = MutableLiveData<Boolean>()
    val showErrorMessage: LiveData<Boolean>
        get() = _showErrorMessage
    private var errorJob: Job? = null

    val users: Flow<PagingData<User>> = githubRepository.getPagedUsers()

    fun onLoadStates(loadStates: CombinedLoadStates) {
        val refresh = loadStates.refresh
        val append = loadStates.append

        // Show progress bar if we're loading
        loading.value = refresh is LoadState.Loading || append is LoadState.Loading

        // Show an error is something went wrong.
        if (refresh is LoadState.Error) {
            showLoadError(refresh)
        } else if (append is LoadState.Error) {
            showLoadError(append)
        }
    }

    private fun showLoadError(error: LoadState.Error) {
        val messageRes = when (val ex = error.error) {
            is HttpException -> when (ex.code()) {
                statusForbidden -> R.string.error_message_rate_limiting
                statusUnauthorized -> R.string.error_message_token
                else -> R.string.error_message_api_error
            }
            is UnknownHostException -> R.string.error_network
            else -> R.string.error_message_unexpected
        }
        showError(messageRes)
    }

    private fun showError(@StringRes messageRes: Int) {
        if (messageRes == 0) {
            return
        }
        // Set current error
        _errorMessage.value = ErrorMessage(messageRes)

        // Reset the timer and show the error message for some period of time.
        // We use this custom view instead of a toast because if you scroll really quickly
        // we don't want to queue a million errors. We just want to show the last error.
        errorJob?.cancel()
        errorJob = viewModelScope.launch(coro.default) {
            _showErrorMessage.postValue(true)
            delay(errorMessageTimeout)
            _showErrorMessage.postValue(false)
        }
    }

    data class ErrorMessage(@StringRes val messageRes: Int)

    @Singleton
    internal class Factory @Inject constructor(
        private val githubRepositoryProvider: Provider<GithubRepo>,
        private val coroProvider: Provider<Coro>,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass != MainActivityViewModel::class.java) {
                throw InvalidClassException("wrong view model class for factory")
            }
            return MainActivityViewModel(
                githubRepository = githubRepositoryProvider.get(),
                coro = coroProvider.get(),
            ) as T
        }
    }

    companion object {
        private const val statusUnauthorized = 401
        private const val statusForbidden = 403
        private const val errorMessageTimeout = 1640L
    }
}