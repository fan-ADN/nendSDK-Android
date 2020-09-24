package net.nend.sample.kotlin.banner.xmllayout

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class XmlSampleActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        XML_SAMPLE(0),
        XML_RESOURCE_SAMPLE(1),
        LISTENER_SAMPLE(2);

        companion object {
            fun getType(id: Int) = values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                XML_SAMPLE -> activity.startActivity(Intent(activity,
                        XmlLayoutActivity::class.java))
                XML_RESOURCE_SAMPLE -> activity.startActivity(Intent(activity,
                        XmlResourceActivity::class.java))
                LISTENER_SAMPLE -> activity.startActivity(Intent(activity,
                        XmlWithListenerActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_sample)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }
}