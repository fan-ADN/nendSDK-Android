package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.RelativeLayout
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

class NoXmlRelativeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml_relative)

        val nendAdView = NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50).apply {
            loadAd()
        }

        (findViewById(R.id.root) as RelativeLayout).run {
            // 中央下部表示の場合
            val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                addRule(RelativeLayout.CENTER_HORIZONTAL)
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                // 上部表示の場合
//                addRule(RelativeLayout.ALIGN_PARENT_TOP)
            }
            addView(nendAdView, params)
        }
    }
}