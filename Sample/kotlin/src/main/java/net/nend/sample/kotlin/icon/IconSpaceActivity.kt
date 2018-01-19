package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdIconView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconSpaceActivity : AppCompatActivity() {

    private lateinit var iconLoader: NendAdIconLoader

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon_space)

        iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
            (1..4).map { i -> resources.getIdentifier("icon$i", "id", packageName) }
                    .forEach { addIconView(findViewById(it) as NendAdIconView) }
            loadAd()
        }
    }

    override fun onPause() {
        super.onPause()
        iconLoader.pause()
    }

    override fun onRestart() {
        super.onRestart()
        iconLoader.resume()
    }
}