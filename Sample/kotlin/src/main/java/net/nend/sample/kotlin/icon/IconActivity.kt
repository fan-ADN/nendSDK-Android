package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.icon.*
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconActivity : AppCompatActivity() {

    private lateinit var iconLoader: NendAdIconLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.icon)

        iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
            addIconView(icon1)
            addIconView(icon2)
            addIconView(icon3)
            addIconView(icon4)
            loadAd()

            setOnReceiveListener {
                Toast.makeText(applicationContext, "Received", Toast.LENGTH_SHORT).show()
            }
            setOnClickListener {
                Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()
            }
            setOnInformationClickListener {
                Toast.makeText(applicationContext,
                        "Clicked information", Toast.LENGTH_SHORT).show()
            }
            setOnFailedListener { iconError ->
                val nendError = iconError.nendError
                when (nendError) {
                    NendAdView.NendError.INVALID_RESPONSE_TYPE -> nendError.message
                    else -> {
                    }
                }
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        iconLoader.resume()
    }

    override fun onPause() {
        super.onPause()
        iconLoader.pause()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.resume -> iconLoader.resume()
            R.id.pause -> iconLoader.pause()
        }
    }
}