package io.explod.paging.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import io.explod.paging.testing.app.AndroidTestApp

class AndroidTestInstrumentationRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application {
        return super.newApplication(cl, AndroidTestApp::class.java.name, context)
    }
}