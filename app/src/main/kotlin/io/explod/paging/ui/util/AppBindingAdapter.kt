package io.explod.paging.ui.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.explod.paging.data.ImageLoader
import io.explod.paging.inject.DataBindingScope
import javax.inject.Inject

@DataBindingScope // Note: Expose dependencies explicitly in AppComponent.
class AppBindingAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
) {

    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visible()
        } else {
            view.gone()
        }
    }

    @BindingAdapter("textRes")
    fun textRes(view: TextView, messageRes: Int?) {
        if (messageRes == null || messageRes == 0) {
            view.text = ""
        } else {
            view.setText(messageRes)
        }
    }

    @BindingAdapter("urlImageCrop")
    fun urlImageCrop(view: ImageView, url: String?) {
        imageLoader.loadCrop(view, url)
    }

    @BindingAdapter("urlImageInside")
    fun urlImageInside(view: ImageView, url: String?) {
        imageLoader.loadInside(view, url)
    }

    @BindingAdapter(value = ["urlImageCropFallback", "fallback"], requireAll = true)
    fun urlImageCropFallback(view: ImageView, url: String?, fallback: Drawable?) {
        imageLoader.loadCrop(view, url, fallback)
    }

    @BindingAdapter(value = ["urlImageInsideFallback", "fallback"], requireAll = true)
    fun urlImageInsideFallback(view: ImageView, url: String?, fallback: Drawable?) {
        imageLoader.loadInside(view, url, fallback)
    }
}