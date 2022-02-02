package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdIconView
import net.nend.sample.kotlin.databinding.NoXmlRelativeBinding
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconNoXmlActivity : AppCompatActivity() {

    private lateinit var binding: NoXmlRelativeBinding
    private lateinit var iconLoader: NendAdIconLoader
    private val wrapContent = LayoutParams.WRAP_CONTENT

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoXmlRelativeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val icon1 = NendAdIconView(applicationContext)
        val icon2 = NendAdIconView(applicationContext)
        val icon3 = NendAdIconView(applicationContext)
        val icon4 = NendAdIconView(applicationContext)

        iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
            addIconView(icon1)
            addIconView(icon2)
            addIconView(icon3)
            addIconView(icon4)
        }

        val params1 = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
            addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            addRule(RelativeLayout.ALIGN_PARENT_TOP)
        }
        val params2 = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
            addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            addRule(RelativeLayout.ALIGN_PARENT_TOP)
        }
        val params3 = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
            addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }
        val params4 = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
            addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        binding.root.addView(icon1, params1)
        binding.root.addView(icon2, params2)
        binding.root.addView(icon3, params3)
        binding.root.addView(icon4, params4)
    }

    override fun onResume() {
        super.onResume()
        iconLoader.loadAd()
    }

    override fun onPause() {
        super.onPause()
        iconLoader.pause()
    }
}