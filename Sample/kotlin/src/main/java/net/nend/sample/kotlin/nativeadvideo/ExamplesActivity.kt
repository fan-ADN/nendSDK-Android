package net.nend.sample.kotlin.nativeadvideo

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class ExamplesActivity : SimpleListActivity() {

    private val list: ArrayList<Class<*>?> = object : ArrayList<Class<*>?>() {
        init {
            add(SimpleActivity::class.java)
            add(InBannerActivity::class.java)
            add(ListViewInFeedActivity::class.java)
            add(GridViewInFeedActivity::class.java)
            add(RecyclerViewInFeedActivity::class.java)
            add(CarouselActivity::class.java)
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
