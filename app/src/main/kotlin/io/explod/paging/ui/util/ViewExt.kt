package io.explod.paging.ui.util

import android.view.View


fun <T : View> T.gone(): T {
    this.visibility = View.GONE
    return this
}

fun <T : View> T.visible(): T {
    this.visibility = View.VISIBLE
    return this
}

fun <T : View> T.invisible(): T {
    this.visibility = View.INVISIBLE
    return this
}