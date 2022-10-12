package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class LayoutSampleActivity : SimpleListActivity() {

    private val list: ArrayList<String> = object : ArrayList<String>() {
        init {
            add(TopActivity::class.java.name)
            add(BottomActivity::class.java.name)
            add(AttachAndDetachActivity::class.java.name)
            add(ListViewActivity::class.java.name)
            add(ViewPagerActivity::class.java.name)
            add(FragmentReplaceActivity::class.java.name)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.layout_sample, list)
    }
}