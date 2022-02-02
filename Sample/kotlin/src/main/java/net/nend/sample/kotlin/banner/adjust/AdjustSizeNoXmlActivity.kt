package net.nend.sample.kotlin.banner.adjust

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdView
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50
import net.nend.sample.kotlin.databinding.NoXmlRelativeBinding

class AdjustSizeNoXmlActivity : AppCompatActivity() {

    private lateinit var binding: NoXmlRelativeBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoXmlRelativeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lp = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT).apply {
            addRule(RelativeLayout.CENTER_HORIZONTAL)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50, true).run {
            binding.root.addView(this, lp)
            loadAd()
        }
    }
}