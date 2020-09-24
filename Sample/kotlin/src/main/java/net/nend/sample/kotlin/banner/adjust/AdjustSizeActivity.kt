package net.nend.sample.kotlin.banner.adjust

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class AdjustSizeActivity : SimpleListActivity() {

    private val list: ArrayList<Class<*>?> = object : ArrayList<Class<*>?>() {
        init {
            add(AdjustSizeXmlLayoutActivity::class.java)
            add(AdjustSizeNoXmlActivity::class.java)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.adjust_size_sample, list)
    }
}