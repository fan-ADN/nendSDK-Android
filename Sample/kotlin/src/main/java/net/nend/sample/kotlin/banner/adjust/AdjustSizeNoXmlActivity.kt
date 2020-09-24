package net.nend.sample.kotlin.banner.adjust

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.no_xml_relative.*
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

class AdjustSizeNoXmlActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml_relative)

        val lp = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.CENTER_HORIZONTAL)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50, true).run {
            root.addView(this, lp)
            loadAd()
        }
    }
}