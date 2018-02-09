package net.nend.sample.kotlin.banner

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class NoXmlLayoutActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        LINEAR_LAYOUT_SAMPLE(0),
        RELATIVE_LAYOUT_SAMPLE(1),
        LISTENER_SAMPLE(2);

        companion object {
            fun getType(id: Int) = SampleType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                LINEAR_LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        NoXmlLinearActivity::class.java))
                RELATIVE_LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        NoXmlRelativeActivity::class.java))
                LISTENER_SAMPLE -> activity.startActivity(Intent(activity,
                        NoXmlWithListenerActivity::class.java))
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_xml)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }
}