package net.nend.sample.kotlin.nativead

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import net.nend.sample.kotlin.R

class NativeSampleActivity : ListActivity() {

    private enum class SampleType(val id: Int) {
        SMALL_SQUARE_SAMPLE(0),
        LARGE_WIDE_SAMPLE(1),
        TEXT_ONLY_SAMPLE(2),
        LIST_VIEW_SAMPLE(3),
        LIST_VIEW_MULTI_SAMPLE(4),
        GRID_VIEW_SAMPLE(5),
        RECYCLER_VIEW_SAMPLE(6),
        VIEWPAGER_SAMPLE(7),
        CAROUSEL_SAMPLE(8),
        CAROUSEL_AUTO_SAMPLE(9),
        MARQUEE_SAMPLE(10),
        GET_AD_SAMPLE(11),
        AUTO_RELOAD_SAMPLE(12),
        V2_LIST_VIEW_SAMPLE(13);

        companion object {
            fun getType(id: Int) = SampleType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                SMALL_SQUARE_SAMPLE, LARGE_WIDE_SAMPLE, TEXT_ONLY_SAMPLE ->
                    activity.startActivity(Intent(activity,
                            NativeLayoutActivity::class.java).putExtra("type", id))
                LIST_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeListActivity::class.java))
                LIST_VIEW_MULTI_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeMultipleLayoutListActivity::class.java))
                GRID_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeGridActivity::class.java))
                RECYCLER_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeRecyclerActivity::class.java))
                VIEWPAGER_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeViewPagerActivity::class.java))
                CAROUSEL_SAMPLE, CAROUSEL_AUTO_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeCarouselAdActivity::class.java).putExtra("type", id))
                MARQUEE_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeMarqueeActivity::class.java))
                GET_AD_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeGetAdDataActivity::class.java))
                AUTO_RELOAD_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeAutoReloadActivity::class.java))
                V2_LIST_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeV2OnListActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_sample)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        SampleType.getType(position).startActivity(this)
    }

    companion object {
        const val NATIVE_SPOT_ID_SMALL_SQUARE = 485516
        const val NATIVE_SPOT_ID_LARGE_WIDE = 485520
        const val NATIVE_SPOT_ID_TEXT_ONLY = 485522
        const val NATIVE_API_KEY_SMALL_SQUARE = "16cb170982088d81712e63087061378c71e8aa5c"
        const val NATIVE_API_KEY_LARGE_WIDE = "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff"
        const val NATIVE_API_KEY_TEXT_ONLY = "2b2381a116290c90b936e409482127efb7123dbc"
        const val NATIVE_LOG_TAG = "NativeAd"
    }
}