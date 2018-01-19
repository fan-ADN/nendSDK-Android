package net.nend.sample.kotlin.banner

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class BannerSampleActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        XML_SAMPLE(0),
        NO_XML_SAMPLE(1),
        LAYOUT_SAMPLE(2),
        DIALOG_SAMPLE(3),
        SIZE_SAMPLE(4),
        ADJUST_SIZE_SAMPLE(5);

        companion object {
            fun getType(id: Int) = SampleType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                XML_SAMPLE -> activity.startActivity(Intent(activity,
                        XmlSampleActivity::class.java))
                NO_XML_SAMPLE -> activity.startActivity(Intent(activity,
                        NoXmlLayoutActivity::class.java))
                LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        LayoutSampleActivity::class.java))
                DIALOG_SAMPLE -> activity.startActivity(Intent(activity,
                        DialogActivity::class.java))
                SIZE_SAMPLE -> activity.startActivity(Intent(activity,
                        SizeSampleActivity::class.java))
                ADJUST_SIZE_SAMPLE -> activity.startActivity(Intent(activity,
                        AdjustSizeActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_sample)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }

    companion object {
        const val BANNER_SPOT_ID_320_50 = 3174
        const val BANNER_API_KEY_320_50 = "c5cb8bc474345961c6e7a9778c947957ed8e1e4f"
    }
}