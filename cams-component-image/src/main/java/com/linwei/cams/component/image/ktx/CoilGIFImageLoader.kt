package com.linwei.cams.component.image.ktx

import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.linwei.cams.component.common.base.CommonBaseApplication

/**
 * 用于加载 Gif 的 Coil ImageLoader
 */
object CoilGIFImageLoader {

    val imageLoader = ImageLoader.Builder(CommonBaseApplication.context)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(CommonBaseApplication.context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
}