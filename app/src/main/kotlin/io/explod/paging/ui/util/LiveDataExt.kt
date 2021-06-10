package io.explod.paging.ui.util

import androidx.lifecycle.MutableLiveData
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun <T> MutableLiveData<Boolean>.postRunningState(block: () -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    postValue(true)
    try {
        return block()
    } finally {
        postValue(false)
    }
}