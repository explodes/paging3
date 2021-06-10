package io.explod.paging.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import io.explod.paging.R
import io.explod.paging.databinding.ActivityMainBinding
import io.explod.paging.inject.InjectorInstance.injector
import io.explod.paging.ui.util.lazyViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactoryProvider: Provider<MainActivityViewModel.Factory>

    @Inject
    internal lateinit var userAdapterFactory: UserAdapter.Factory

    private val viewModel by lazyViewModel<MainActivityViewModel> { viewModelFactoryProvider.get() }

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)

        adapter = userAdapterFactory.create(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recycler.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onLoadStates(loadStates)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.users.collectLatest { adapter.submitData(it) }
        }
    }
}