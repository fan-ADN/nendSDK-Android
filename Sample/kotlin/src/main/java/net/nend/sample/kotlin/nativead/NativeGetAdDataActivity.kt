package net.nend.sample.kotlin.nativead

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.native_get_ad_data.*
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE

/**
 * 広告オブジェクトから広告の各データを取得しレイアウトする、最もシンプルな実装方法
 */
class NativeGetAdDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_get_ad_data)

        // NendAdNativeClient インスタンス生成
        NendAdNativeClient(this, NATIVE_SPOT_ID_LARGE_WIDE, NATIVE_API_KEY_LARGE_WIDE).run {
            // 広告オブジェクトのロード
            loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告取得成功")

                    // 広告オブジェクトからテキストを取得する
                    title_text.text = nendAdNative.titleText
                    pr_text.text = NendAdNative.AdvertisingExplicitly.PR.text
                    content_text.text = nendAdNative.contentText
                    action_button.text = nendAdNative.actionText
                    promotion_name.text = nendAdNative.promotionName
                    promotion_url.text = nendAdNative.promotionUrl

                    // 広告オブジェクトから広告画像を取得する
                    nendAdNative.downloadAdImage(object : NendAdNative.Callback {
                        override fun onSuccess(bitmap: Bitmap) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告画像取得成功")
                            ad_image.setImageBitmap(bitmap)
                        }

                        override fun onFailure(e: Exception) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG,
                                    "広告画像取得失敗: ${e.message}")
                        }
                    })
                    // 広告オブジェクトからロゴ画像を取得する
                    nendAdNative.downloadLogoImage(object : NendAdNative.Callback {
                        override fun onSuccess(bitmap: Bitmap) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "ロゴ取得成功")
                            logo_image.setImageBitmap(bitmap)
                        }

                        override fun onFailure(e: Exception) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG,
                                    "ロゴ画像取得失敗: ${e.message}")
                        }
                    })
                    // 広告をアクティブにする
                    nendAdNative.activate(ad_container, pr_text)

                    // 広告オブジェクトから広告画像、ロゴ画像のDL用URLも取得可能
                    ad_image_url.text = nendAdNative.adImageUrl
                    logo_image_url.text = nendAdNative.logoImageUrl
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG,
                            "広告取得失敗: ${nendError.message}")
                }
            })
        }
    }
}