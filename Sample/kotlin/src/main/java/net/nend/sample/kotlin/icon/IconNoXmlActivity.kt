package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.no_xml_relative.*
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdIconView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconNoXmlActivity : AppCompatActivity() {

    private lateinit var iconLoader: NendAdIconLoader
    private val wrapContent = LayoutParams.WRAP_CONTENT

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml_relative)

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

        root.addView(icon1, params1)
        root.addView(icon2, params2)
        root.addView(icon3, params3)
        root.addView(icon4, params4)
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