package net.nend.sample.kotlin.nativeadvideo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import net.nend.android.*
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewBinder
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder

class SimpleActivity : AppCompatActivity() {
    private lateinit var videoBinder: MyNendAdViewBinder
    private lateinit var normalBinder: NendAdNativeViewBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_video_simple)

        videoBinder = MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.native_video_ad_mediaview)
                .logoImageId(R.id.native_video_ad_logo_image)
                .titleId(R.id.native_video_ad_title)
                .contentId(R.id.native_video_ad_content)
                .advertiserId(R.id.native_video_ad_advertiser)
                .ratingId(R.id.native_video_ad_rating_stars)
                .ratingCountId(R.id.native_video_ad_rating_count)
                .actionId(R.id.native_video_ad_action)
                .build()
        normalBinder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.native_video_ad_image)
                .logoImageId(R.id.native_video_ad_logo_image)
                .titleId(R.id.native_video_ad_title)
                .contentId(R.id.native_video_ad_content)
                .prId(R.id.native_video_ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .promotionNameId(R.id.native_video_ad_advertiser)
                .actionId(R.id.native_video_ad_action)
                .build()
        val itemView = findViewById<View>(R.id.include_native_inflater_video)

        val videoLoader = NendAdNativeVideoLoader(this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY).apply {
            setFillerImageNativeAd(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff")
        }
        videoLoader.setLocationEnabled(false)
        videoLoader.loadAd(object : NendAdNativeVideoLoader.Callback {
            override fun onSuccess(ad: NendAdNativeVideo) {
                if (ad.hasVideo()) {
                    val holder = MyNendAdViewHolder(itemView, videoBinder)
                    videoBinder.renderView(holder, ad, object : NendAdNativeMediaViewListener {
                        override fun onStartPlay(nendAdNativeMediaView: NendAdNativeMediaView) {

                        }

                        override fun onStopPlay(nendAdNativeMediaView: NendAdNativeMediaView) {

                        }

                        override fun onCompletePlay(nendAdNativeMediaView: NendAdNativeMediaView) {

                        }

                        override fun onOpenFullScreen(nendAdNativeMediaView: NendAdNativeMediaView) {

                        }

                        override fun onCloseFullScreen(nendAdNativeMediaView: NendAdNativeMediaView) {

                        }

                        override fun onError(i: Int, s: String) {

                        }
                    })
                } else {
                    findViewById<View>(R.id.native_video_ad_mediaview).visibility = View.GONE
                    findViewById<View>(R.id.native_video_ad_rating_stars).visibility = View.GONE
                    findViewById<View>(R.id.native_video_ad_rating_count).visibility = View.GONE
                    findViewById<View>(R.id.native_video_ad_pr).visibility = View.VISIBLE

                    val adNormalNative = ad.fallbackAd
                    adNormalNative.intoView(itemView, normalBinder)
                }
            }

            override fun onFailure(error: Int) {

            }
        })
    }
}
