package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

class NoXmlLinearActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml_linear)

        val nendAdView = NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50).apply {
            loadAd()
        }

        (findViewById<LinearLayout>(R.id.root)).apply {
            // 中央下部表示の場合
            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            // 上部表示の場合
//            gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
            addView(nendAdView,
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT))
        }
    }
}