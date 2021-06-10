package io.explod.paging.data

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {
    fun loadInside(img: ImageView, url: String?, fallback: Drawable? = null)
    fun loadCrop(img: ImageView, url: String?, fallback: Drawable? = null)
}