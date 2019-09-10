package net.nend.sample.kotlin.icon

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class IconSampleActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        XML_SAMPLE(0),
        XML_SEPARATE_SAMPLE(1),
        XML_RESOURCE_FILE_SAMPLE(2),
        NO_XML_SAMPLE(3),
        DIALOG_SAMPLE(4),
        ATTACH_DETACH_SAMPLE(5),
        SPACE_SAMPLE(6);

        companion object {
            fun getType(id: Int) = values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                XML_SAMPLE -> activity.startActivity(Intent(activity,
                        IconLayoutActivity::class.java))
                XML_SEPARATE_SAMPLE -> activity.startActivity(Intent(activity,
                        IconActivity::class.java))
                XML_RESOURCE_FILE_SAMPLE -> activity.startActivity(Intent(activity,
                        IconResourceActivity::class.java))
                NO_XML_SAMPLE -> activity.startActivity(Intent(activity,
                        IconNoXmlActivity::class.java))
                DIALOG_SAMPLE -> activity.startActivity(Intent(activity,
                        IconDialogActivity::class.java))
                ATTACH_DETACH_SAMPLE -> activity.startActivity(Intent(activity,
                        IconAttachAndDetachActivity::class.java))
                SPACE_SAMPLE -> activity.startActivity(Intent(activity,
                        IconSpaceActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icom_sample)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }

    companion object {
        const val ICON_SPOT_ID = 101282
        const val ICON_API_KEY = "0c734134519f25412ae9a9bff94783b81048ffbe"
    }
}