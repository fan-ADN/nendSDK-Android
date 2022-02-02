package net.nend.sample.kotlin.icon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdIconLoader
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.databinding.IconBinding
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconActivity : AppCompatActivity() {

    private lateinit var binding: IconBinding
    private lateinit var iconLoader: NendAdIconLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
            addIconView(binding.icon1)
            addIconView(binding.icon2)
            addIconView(binding.icon3)
            addIconView(binding.icon4)
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
                when (val error = iconError.nendError) {
                    NendAdView.NendError.INVALID_RESPONSE_TYPE -> error.message
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