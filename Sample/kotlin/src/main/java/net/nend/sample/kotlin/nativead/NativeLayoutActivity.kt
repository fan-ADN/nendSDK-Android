package net.nend.sample.kotlin.nativead

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeListener
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_TEXT_ONLY
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_TEXT_ONLY

class NativeLayoutActivity : AppCompatActivity() {

    private enum class NativeType(val id: Int) {
        LARGE(1),
        TEXT_ONLY(2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spotId: Int
        val apiKey: String
        val layout: Int
        val type = intent.extras?.getInt("type") ?: return

        when (type) {
            NativeType.LARGE.id -> {
                layout = R.layout.native_large_wide
                spotId = NATIVE_SPOT_ID_LARGE_WIDE
                apiKey = NATIVE_API_KEY_LARGE_WIDE
            }
            NativeType.TEXT_ONLY.id -> {
                layout = R.layout.native_text_only
                spotId = NATIVE_SPOT_ID_TEXT_ONLY
                apiKey = NATIVE_API_KEY_TEXT_ONLY
            }
            else -> {
                layout = R.layout.native_small_square
                spotId = NATIVE_SPOT_ID_SMALL_SQUARE
                apiKey = NATIVE_API_KEY_SMALL_SQUARE
            }
        }

        setContentView(layout)

        val binder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .logoImageId(R.id.logo_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
                .promotionUrlId(R.id.ad_promotion_url)
                .promotionNameId(R.id.ad_promotion_name)
                .actionId(R.id.ad_action)
                .build()

        NendAdNativeClient(this, spotId, apiKey).run {
            loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告取得成功")
                    nendAdNative.intoView(findViewById(R.id.ad), binder)
                    nendAdNative.setNendAdNativeListener(object : NendAdNativeListener {
                        override fun onImpression(nendAdNative: NendAdNative) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "onImpression")
                        }

                        override fun onClickAd(nendAdNative: NendAdNative) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "onClickAd")
                        }

                        override fun onClickInformation(nendAdNative: NendAdNative) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "onClickInformation")
                        }
                    })
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                }
            })
        }
    }
}