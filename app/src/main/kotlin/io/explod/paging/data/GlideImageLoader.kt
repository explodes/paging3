package io.explod.paging.data

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import dagger.Reusable
import io.explod.paging.app.App
import javax.inject.Inject

@Reusable
class GlideImageLoader @Inject constructor(app: App) : ImageLoader {

    private val glide = Glide.with(app)

    override fun loadInside(img: ImageView, url: String?, fallback: Drawable?) {
        glide.load(url).fitCenter().placeholder(fallback).fallback(fallback).into(img)
    }

    override fun loadCrop(img: ImageView, url: String?, fallback: Drawable?) {
        glide.load(url).centerCrop().placeholder(fallback).fallback(fallback).into(img)
    }
}