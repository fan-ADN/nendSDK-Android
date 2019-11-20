package net.nend.sample.kotlin.interstitial

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import net.nend.android.NendAdInterstitial
import net.nend.sample.kotlin.R

class InterstitialActivity : AppCompatActivity(),
        NendAdInterstitial.OnClickListener, NendAdInterstitial.OnCompletionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.interstitial_ad)

        NendAdInterstitial.loadAd(applicationContext,
                "8c278673ac6f676dae60a1f56d16dad122e23516", 213206)
        // 必要に応じて広告取得結果通知を受けとる
        NendAdInterstitial.setListener(this)

        (findViewById<Button>(R.id.button)).setOnClickListener {
            // 表示結果が返却される
            val result = NendAdInterstitial.showAd(this)
            // 表示結果に応じて処理を行う
            when (result) {
                NendAdInterstitial.NendAdInterstitialShowResult.AD_SHOW_SUCCESS -> {
                }
                NendAdInterstitial.NendAdInterstitialShowResult.AD_SHOW_ALREADY -> {
                }
                NendAdInterstitial.NendAdInterstitialShowResult.AD_FREQUENCY_NOT_REACHABLE -> {
                }
                NendAdInterstitial.NendAdInterstitialShowResult.AD_REQUEST_INCOMPLETE -> {
                }
                NendAdInterstitial.NendAdInterstitialShowResult.AD_LOAD_INCOMPLETE -> {
                }
                NendAdInterstitial.NendAdInterstitialShowResult.AD_DOWNLOAD_INCOMPLETE -> {
                }
                else -> {
                }
            }
            // 広告表示結果をログに出力
            Log.d(TAG, result.name)
            // ５秒後に広告を閉じる
            Handler().postDelayed({ NendAdInterstitial.dismissAd() }, 5000)
        }
    }

    /**
     * インタースティシャル広告クリック通知
     */
    override fun onClick(clickType: NendAdInterstitial.NendAdInterstitialClickType) {
        // クリックに応じて処理行う
        when (clickType) {
            NendAdInterstitial.NendAdInterstitialClickType.CLOSE -> {
            }
            NendAdInterstitial.NendAdInterstitialClickType.DOWNLOAD -> {
            }
            else -> {
            }
        }
        // 広告クリックをログに出力
        Log.d(TAG, clickType.name)
    }

    /**
     * 広告受信通知
     */
    override fun onCompletion(statusCode: NendAdInterstitial.NendAdInterstitialStatusCode) {
        // 受信結果に応じて処理を行う
        when (statusCode) {
            NendAdInterstitial.NendAdInterstitialStatusCode.SUCCESS -> {
            }
            NendAdInterstitial.NendAdInterstitialStatusCode.FAILED_AD_DOWNLOAD -> {
            }
            NendAdInterstitial.NendAdInterstitialStatusCode.INVALID_RESPONSE_TYPE -> {
            }
            NendAdInterstitial.NendAdInterstitialStatusCode.FAILED_AD_REQUEST -> {
            }
            else -> {
            }
        }
        // 広告受信結果をログに出力
        Log.d(TAG, statusCode.name)
    }

    companion object {
        private const val TAG = "InterstitialAd"
    }
}