package net.nend.sample.kotlin.nativeadvideo

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class ExamplesActivity : SimpleListActivity() {

    private val list: ArrayList<String> = object : ArrayList<String>() {
        init {
            add(SimpleActivity::class.java.name)
            add(InBannerActivity::class.java.name)
            add(ListViewInFeedActivity::class.java.name)
            add(GridViewInFeedActivity::class.java.name)
            add(RecyclerViewInFeedActivity::class.java.name)
            add(CarouselActivity::class.java.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.activity_native_video_examples, list)
    }

    companion object {
        const val LOG_TAG = "NativeVideoAd"
        const val NATIVE_VIDEO_SPOT_ID = 887591
        const val NATIVE_VIDEO_API_KEY = "a284d892c3617bf5705facd3bfd8e9934a8b2491"
    }
}
