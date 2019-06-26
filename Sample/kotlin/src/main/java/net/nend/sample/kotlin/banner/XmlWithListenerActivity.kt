package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import net.nend.android.NendAdInformationListener
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R

class XmlWithListenerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_layout)

        (findViewById(R.id.nend) as NendAdView).apply {
            setListener(object : NendAdInformationListener {
                override fun onReceiveAd(adView: NendAdView) {
                    Toast.makeText(applicationContext, "広告取得成功!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailedToReceiveAd(adView: NendAdView) {
                    Toast.makeText(applicationContext, "広告取得失敗!", Toast.LENGTH_SHORT).show()
                }

                override fun onClick(adView: NendAdView) {
                    Toast.makeText(applicationContext, "クリック成功!", Toast.LENGTH_SHORT).show()
                }

                override fun onDismissScreen(adView: NendAdView) {
                    Toast.makeText(applicationContext, "復帰成功!", Toast.LENGTH_SHORT).show()
                }

                override fun onInformationButtonClick(adView: NendAdView) {
                    Toast.makeText(applicationContext,
                            "Informationボタンクリック成功！", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}