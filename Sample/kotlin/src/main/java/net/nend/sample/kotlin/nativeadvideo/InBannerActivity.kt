package net.nend.sample.kotlin.nativeadvideo

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import net.nend.android.NendAdNativeMediaView
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeVideoLoader
import net.nend.sample.kotlin.R

class InBannerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_video_in_banner)

        loadAd()
    }

    private fun loadAd() {
        val loader = NendAdNativeVideoLoader(
                this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY
        )
        loader.loadAd(object : NendAdNativeVideoLoader.Callback {
            override fun onSuccess(ad: NendAdNativeVideo) {
                val item = findViewById<FrameLayout>(R.id.movie_area)

                val mediaView = NendAdNativeMediaView(this@InBannerActivity)
                mediaView.setMedia(ad)
                item.addView(mediaView)

                val title = findViewById<TextView>(R.id.title_native_in_banner)
                title.text = ad.titleText
                val description = findViewById<TextView>(R.id.description_native_in_banner)
                description.text = ad.descriptionText
            }

            override fun onFailure(errorCode: Int) {

            }
        })
    }
}
