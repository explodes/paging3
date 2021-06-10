package io.explod.paging.inject

import dagger.Binds
import dagger.Module
import io.explod.paging.data.GlideImageLoader
import io.explod.paging.data.ImageLoader

@Module
interface ImageLoaderModule {
    @Binds
    fun imageLoader(impl: GlideImageLoader): ImageLoader
}