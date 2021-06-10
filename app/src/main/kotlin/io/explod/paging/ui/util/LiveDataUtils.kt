package io.explod.paging.ui.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T1, T2, R> combine(
    sourceA: LiveData<out T1>,
    sourceB: LiveData<out T2>,
    map: (a: T1, b: T2) -> R,
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        var a: T1? = null
        var b: T2? = null

        fun maybePost() {
            val newA = a
            val newB = b
            if (newA != null && newB != null) {
                postValue(map(newA, newB))
            }
        }

        addSource(sourceA) {
            a = it
            maybePost()
        }

        addSource(sourceB) {
            b = it
            maybePost()
        }
    }
}


fun <T1, T2, T3, R> combine(
    sourceA: LiveData<out T1>,
    sourceB: LiveData<out T2>,
    sourceC: LiveData<out T3>,
    map: (a: T1, b: T2, c: T3) -> R,
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        var a: T1? = null
        var b: T2? = null
        var c: T3? = null

        fun maybePost() {
            val newA = a
            val newB = b
            val newC = c
            if (newA != null && newB != null && newC != null) {
                postValue(map(newA, newB, newC))
            }
        }

        addSource(sourceA) {
            a = it
            maybePost()
        }
        addSource(sourceB) {
            b = it
            maybePost()
        }
        addSource(sourceC) {
            c = it
            maybePost()
        }
    }
}

fun <T1, T2, T3, T4, R> combine(
    sourceA: LiveData<out T1>,
    sourceB: LiveData<out T2>,
    sourceC: LiveData<out T3>,
    sourceD: LiveData<out T4>,
    map: (a: T1, b: T2, c: T3, d: T4) -> R,
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        var a: T1? = null
        var b: T2? = null
        var c: T3? = null
        var d: T4? = null

        fun maybePost() {
            val newA = a
            val newB = b
            val newC = c
            val newD = d
            if (newA != null && newB != null && newC != null && newD != null) {
                postValue(map(newA, newB, newC, newD))
            }
        }

        addSource(sourceA) {
            a = it
            maybePost()
        }
        addSource(sourceB) {
            b = it
            maybePost()
        }
        addSource(sourceC) {
            c = it
            maybePost()
        }
        addSource(sourceD) {
            d = it
            maybePost()
        }
    }
}

fun <T1, T2, T3, T4, T5, R> combine(
    sourceA: LiveData<out T1>,
    sourceB: LiveData<out T2>,
    sourceC: LiveData<out T3>,
    sourceD: LiveData<out T4>,
    sourceE: LiveData<out T5>,
    map: (a: T1, b: T2, c: T3, d: T4, e: T5) -> R,
): LiveData<R> {
    return MediatorLiveData<R>().apply {
        var a: T1? = null
        var b: T2? = null
        var c: T3? = null
        var d: T4? = null
        var e: T5? = null

        fun maybePost() {
            val newA = a
            val newB = b
            val newC = c
            val newD = d
            val newE = e
            if (newA != null && newB != null && newC != null && newD != null && newE != null) {
                postValue(map(newA, newB, newC, newD, newE))
            }
        }

        addSource(sourceA) {
            a = it
            maybePost()
        }
        addSource(sourceB) {
            b = it
            maybePost()
        }
        addSource(sourceC) {
            c = it
            maybePost()
        }
        addSource(sourceD) {
            d = it
            maybePost()
        }
        addSource(sourceE) {
            e = it
            maybePost()
        }
    }
}