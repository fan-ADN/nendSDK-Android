package net.nend.sample.kotlin.banner

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class AdjustSizeActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        ADJUST_SIZE_XML(0),
        ADJUST_SIZE_NO_XML(1);

        companion object {
            fun getType(id: Int) = values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                ADJUST_SIZE_XML -> activity.startActivity(Intent(activity,
                        AdjustSizeXmlLayoutActivity::class.java))
                ADJUST_SIZE_NO_XML -> activity.startActivity(Intent(activity,
                        AdjustSizeNoXmlActivity::class.java))
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adjust_size_sample)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }
}