package net.nend.sample.kotlin.nativeadvideo

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class ExamplesActivity : ListActivity() {

    private enum class Examples(val id: Int) {
        SIMPLE(0),
        IN_BANNER(1),
        LIST_VIEW(2),
        GRID_VIEW(3),
        RECYCLER_VIEW(4),
        CAROUSEL(5);

        companion object {
            fun get(id: Int) = Examples.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                SIMPLE -> activity.startActivity(Intent(activity,
                        SimpleActivity::class.java))
                IN_BANNER -> activity.startActivity(Intent(activity,
                        InBannerActivity::class.java))
                LIST_VIEW -> activity.startActivity(Intent(activity,
                        ListViewInFeedActivity::class.java))
                GRID_VIEW -> activity.startActivity(Intent(activity,
                        GridViewInFeedActivity::class.java))
                RECYCLER_VIEW -> activity.startActivity(Intent(activity,
                        RecyclerViewInFeedActivity::class.java))
                CAROUSEL -> activity.startActivity(Intent(activity,
                        CarouselActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_video_examples)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        Examples.get(position).startActivity(this)
    }

    companion object {
        const val LOG_TAG = "NativeVideoAd"
        const val NATIVE_VIDEO_SPOT_ID = 887591
        const val NATIVE_VIDEO_API_KEY = "a284d892c3617bf5705facd3bfd8e9934a8b2491"
    }
}
