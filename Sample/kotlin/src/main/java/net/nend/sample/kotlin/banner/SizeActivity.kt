package net.nend.sample.kotlin.banner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.nend.sample.kotlin.R

class SizeActivity : AppCompatActivity() {

    private enum class SampleType(val id: Int) {
        SIZE_320_50(0),
        SIZE_320_100(1),
        SIZE_300_100(2),
        SIZE_300_250(3),
        SIZE_728_90(4);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent.getIntExtra("size_type", 0)) {
            SampleType.SIZE_320_50.id -> setContentView(R.layout.ad_320x50)
            SampleType.SIZE_320_100.id -> setContentView(R.layout.ad_320x100)
            SampleType.SIZE_300_100.id -> setContentView(R.layout.ad_300x100)
            SampleType.SIZE_300_250.id -> setContentView(R.layout.ad_300x250)
            SampleType.SIZE_728_90.id -> setContentView(R.layout.ad_728x90)
        }
    }
}