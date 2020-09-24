package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class LayoutSampleActivity : SimpleListActivity() {

    private val list: ArrayList<Class<*>?> = object : ArrayList<Class<*>?>() {
        init {
            add(TopActivity::class.java)
            add(BottomActivity::class.java)
            add(AttachAndDetachActivity::class.java)
            add(ListViewActivity::class.java)
            add(ViewPagerActivity::class.java)
            add(PagerAndListActivity::class.java)
            add(FragmentReplaceActivity::class.java)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.layout_sample, list)
    }
}