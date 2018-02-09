package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.icon_attach_detach.*
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdIconView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID
import java.util.*

class IconAttachAndDetachActivity : AppCompatActivity() {

    private lateinit var iconLoader: NendAdIconLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon_attach_detach)

        val view1 = NendAdIconView(applicationContext)
        val view2 = NendAdIconView(applicationContext)
        val view3 = NendAdIconView(applicationContext)
        val view4 = NendAdIconView(applicationContext)

        iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
            addIconView(view1)
            addIconView(view2)
            addIconView(view3)
            addIconView(view4)
            loadAd()
        }

        icon_container.run {
            addView(view1)
            addView(view2)
            addView(view3)
            addView(view4)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickAdd(view: View) {
        if (iconLoader.iconCount >= 4) {
            return
        }
        NendAdIconView(applicationContext).apply {
            iconLoader.addIconView(this)
            icon_container.addView(this)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickRemove(view: View) {
        if (iconLoader.iconCount == 0) {
            return
        }
        val index = Random().nextInt(iconLoader.iconCount)
        val iconView = icon_container.getChildAt(index) as NendAdIconView
        iconLoader.removeIconView(iconView)
        icon_container.removeViewAt(index)
    }
}