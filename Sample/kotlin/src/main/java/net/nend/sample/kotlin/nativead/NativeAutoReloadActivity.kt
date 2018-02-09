package net.nend.sample.kotlin.nativead

import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.native_auto_reload.*
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE


/**
 * 自動リロード実装サンプル
 */
class NativeAutoReloadActivity : AppCompatActivity() {

    private lateinit var client: NendAdNativeClient
    private lateinit var countDownTimer: CountDownTimer
    private var nendAd: NendAdNative? = null
    private var isEnableAutoReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_auto_reload)

        client = NendAdNativeClient(this,
                NATIVE_SPOT_ID_LARGE_WIDE, NATIVE_API_KEY_LARGE_WIDE).apply {
            // 広告オブジェクトの取得
            loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功")
                    nendAd = nendAdNative
                    setAdTexts()
                    getAndSetAdImage()
                    activateAd()
                    setClickListener()
                    setAutoReload()
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                }
            })
        }
    }

    // 広告オブジェクトから文言を取得
    private fun setAdTexts() {
        title_text.text = nendAd?.titleText
        content_text.text = nendAd?.contentText
        pr_text.text = NendAdNative.AdvertisingExplicitly.AD.text
    }

    // 広告オブジェクトから画像を取得
    private fun getAndSetAdImage() {
        nendAd?.downloadAdImage(object : NendAdNative.Callback {
            override fun onSuccess(bitmap: Bitmap) {
                Log.i(NATIVE_LOG_TAG, "画像取得成功")
                ad_image.setImageBitmap(bitmap)
            }

            override fun onFailure(e: Exception) {
                Log.i(NATIVE_LOG_TAG, "画像取得失敗: ${e.message}")
            }
        })
    }

    // 広告をアクティブにする
    private fun activateAd() {
        nendAd?.activate(ad_container, pr_text)
    }

    // クリックリスナーの付与
    private fun setClickListener() {
        nendAd?.setOnClickListener { Log.i(NATIVE_LOG_TAG, "クリック") }
    }

    // 自動リロードのサンプル
    private fun setAutoReload() {
        if (!isEnableAutoReload) {
            isEnableAutoReload = true
            progressCount()
            client.enableAutoReload(
                    AUTO_RELOAD_INTERVAL.toLong(), object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功（自動リロード）")
                    nendAd = nendAdNative
                    setAdTexts()
                    getAndSetAdImage()
                    activateAd()
                    setClickListener()
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗（自動リロード）")
                    isEnableAutoReload = false
                    client.disableAutoReload()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isEnableAutoReload) {
            Log.i(NATIVE_LOG_TAG, "自動リロード開始")
            setAutoReload()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isEnableAutoReload) {
            Log.i(NATIVE_LOG_TAG, "自動リロード停止")
            isEnableAutoReload = false
            client.disableAutoReload()
            countDownTimer.cancel()
        }
    }

    private fun progressCount() {
        countDownTimer = object : CountDownTimer(AUTO_RELOAD_INTERVAL.toLong(), 100) {
            override fun onFinish() {
                start()
            }

            override fun onTick(amount: Long) {
                progressBar.progress = (AUTO_RELOAD_INTERVAL - amount).toInt() / 100
            }
        }
        countDownTimer.start()
    }

    companion object {
        private const val AUTO_RELOAD_INTERVAL = 30000
    }
}