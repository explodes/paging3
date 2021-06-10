package io.explod.paging

import android.widget.ImageView
import com.google.common.truth.Truth.assertThat
import io.explod.paging.data.ImageLoader
import io.explod.paging.testing.UnitTest
import io.mockk.every
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class UnitTestTest : UnitTest() {

    @Inject
    internal lateinit var imageLoader: ImageLoader

    override fun performInjection() {
        injector.inject(this)
    }


    @Test
            /**
             * Demonstrates that the ImageLoader provided by the UnitTestAppComponent is in fact a mock,
             * which means that it is not using network resources in unit tests.
             */
    fun `injected imageLoader is mockk`() {
        val imageUrl = "http://fake"
        val imageLoadCount = AtomicInteger(0)
        every {
            imageLoader.loadCrop(any(), eq(imageUrl))
        } answers {
            imageLoadCount.incrementAndGet()
        }

        imageLoader.loadCrop(ImageView(app), imageUrl)

        assertThat(imageLoadCount.get()).isEqualTo(1)
    }
}