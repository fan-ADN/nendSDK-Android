package net.nend.sample.kotlin.banner.noxml

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdListener
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

class NoXmlWithListenerActivity : AppCompatActivity(), NendAdListener { // 1) implements


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml_relative)

        val nendAdView = NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50).also {
            // 2) リスナーを登録
            it.setListener(this)
            it.loadAd()
        }

        (findViewById<RelativeLayout>(R.id.root)).run {
            // 中央上部表示の場合
            val params = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.CENTER_HORIZONTAL)
                addRule(RelativeLayout.ALIGN_PARENT_TOP)
            }
            addView(nendAdView, params)
        }
    }

    // 3)  通知を受けるメソッドを用意
    /** 受信エラー通知  */
    override fun onFailedToReceiveAd(nendAdView: NendAdView) {
        Toast.makeText(applicationContext, "onFailedToReceiveAd", Toast.LENGTH_LONG).show()
    }

    /** 受信成功通知  */
    override fun onReceiveAd(nendAdView: NendAdView) {
        Toast.makeText(applicationContext, "onReceiveAd", Toast.LENGTH_LONG).show()
    }

    /** クリック通知  */
    override fun onClick(nendAdView: NendAdView) {
        Toast.makeText(applicationContext, "onClick", Toast.LENGTH_LONG).show()
    }

    /** 復帰通知  */
    override fun onDismissScreen(arg0: NendAdView) {
        Toast.makeText(applicationContext, "onDismissScreen", Toast.LENGTH_LONG).show()
    }
}