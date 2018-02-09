package net.nend.sample.kotlin.banner

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class LayoutSampleActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        TOP_LAYOUT_SAMPLE(0),
        BOTTOM_LAYOUT_SAMPLE(1),
        ATTACH_DETACH_SAMPLE(2),
        LIST_SAMPLE(3),
        VIEWPAGER_SAMPLE(4),
        VIEWPAGER_LIST_SAMPLE(5),
        FRAGMENT_SAMPLE(6);

        companion object {
            fun getType(id: Int) = SampleType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                TOP_LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        TopActivity::class.java))
                BOTTOM_LAYOUT_SAMPLE -> activity.startActivity(Intent(activity,
                        BottomActivity::class.java))
                ATTACH_DETACH_SAMPLE -> activity.startActivity(Intent(activity,
                        AttachAndDetachActivity::class.java))
                LIST_SAMPLE -> activity.startActivity(Intent(activity,
                        ListViewActivity::class.java))
                VIEWPAGER_SAMPLE -> activity.startActivity(Intent(activity,
                        ViewPagerActivity::class.java))
                VIEWPAGER_LIST_SAMPLE -> activity.startActivity(Intent(activity,
                        PagerAndListActivity::class.java))
                FRAGMENT_SAMPLE -> activity.startActivity(Intent(activity,
                        FragmentReplaceActivity::class.java))
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sample)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }
}