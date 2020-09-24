package net.nend.sample.kotlin.nativead

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import net.nend.sample.kotlin.R
import java.util.*

class NativeSampleActivity : AppCompatActivity() {

    private enum class SampleType {
        SMALL_SQUARE_SAMPLE,
        LARGE_WIDE_SAMPLE,
        TEXT_ONLY_SAMPLE,
        LIST_VIEW_SAMPLE,
        V2_LIST_VIEW_SAMPLE,
        GRID_VIEW_SAMPLE,
        RECYCLER_VIEW_SAMPLE,
        VIEWPAGER_SAMPLE,
        CAROUSEL_SAMPLE,
        MARQUEE_SAMPLE,
        GET_AD_SAMPLE,
        AUTO_RELOAD_SAMPLE;

        companion object {
            fun getType(id: Int) = values().first { it.ordinal == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                SMALL_SQUARE_SAMPLE, LARGE_WIDE_SAMPLE, TEXT_ONLY_SAMPLE ->
                    activity.startActivity(Intent(activity,
                            NativeLayoutActivity::class.java).putExtra("type", ordinal))
                LIST_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeMultipleLayoutListActivity::class.java))
                GRID_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeGridActivity::class.java))
                RECYCLER_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeRecyclerActivity::class.java))
                VIEWPAGER_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeViewPagerActivity::class.java))
                CAROUSEL_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeCarouselAdActivity::class.java).putExtra("type", ordinal))
                MARQUEE_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeMarqueeActivity::class.java))
                GET_AD_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeAdV2Activity::class.java))
                AUTO_RELOAD_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeAutoReloadActivity::class.java))
                V2_LIST_VIEW_SAMPLE -> activity.startActivity(Intent(activity,
                        NativeV2OnListActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateListFragment()
    }

    private fun instantiateListFragment() {
        val id = Random().nextInt(0xFFFF)
        val container = FrameLayout(this)
        container.id = id
        setContentView(container, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        supportFragmentManager
                .beginTransaction()
                .add(id, SampleListFragment(R.layout.activity_native_sample))
                .commit()
    }

    class SampleListFragment(private val resId: Int) : ListFragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            setHasOptionsMenu(true)
            return inflater.inflate(resId, container, false)
        }

        override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
            activity?.let { SampleType.getType(position).startActivity(it) }
        }
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