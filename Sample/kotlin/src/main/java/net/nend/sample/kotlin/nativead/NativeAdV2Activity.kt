package net.nend.sample.kotlin.nativead

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.sample.kotlin.databinding.NativeGetAdDataBinding
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE

class NativeAdV2Activity : AppCompatActivity() {

    private lateinit var binding: NativeGetAdDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NativeGetAdDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NendAdNativeClient インスタンス生成
        NendAdNativeClient(this, NATIVE_SPOT_ID_LARGE_WIDE, NATIVE_API_KEY_LARGE_WIDE).run {
            // 広告オブジェクトのロード
            loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告取得成功")

                    // 広告オブジェクトからテキストを取得する
                    binding.run {
                        titleText.text = nendAdNative.titleText
                        contentText.text = NendAdNative.AdvertisingExplicitly.PR.text
                        actionButton.text = nendAdNative.actionText
                        promotionName.text = nendAdNative.promotionName
                        promotionUrl.text = nendAdNative.promotionUrl
                    }

                    // 広告オブジェクトから広告画像を取得する
                    nendAdNative.downloadAdImage(object : NendAdNative.Callback {
                        override fun onSuccess(bitmap: Bitmap) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG, "広告画像取得成功")
                            binding.adImage.setImageBitmap(bitmap)
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
                            binding.logoImage.setImageBitmap(bitmap)
                        }

                        override fun onFailure(e: Exception) {
                            Log.i(NativeSampleActivity.NATIVE_LOG_TAG,
                                    "ロゴ画像取得失敗: ${e.message}")
                        }
                    })
                    // 広告をアクティブにする
                    nendAdNative.activate(binding.adContainer, binding.prText)

                    // 広告オブジェクトから広告画像、ロゴ画像のDL用URLも取得可能
                    binding.adImageUrl.text = nendAdNative.adImageUrl
                    binding.logoImageUrl.text = nendAdNative.logoImageUrl
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NativeSampleActivity.NATIVE_LOG_TAG,
                            "広告取得失敗: ${nendError.message}")
                }
            })
        }
    }
}