package net.nend.sample.kotlin.banner.adjust

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class AdjustSizeActivity : SimpleListActivity() {

    private val list: ArrayList<String> = object : ArrayList<String>() {
        init {
            add(AdjustSizeXmlLayoutActivity::class.java.name)
            add(AdjustSizeNoXmlActivity::class.java.name)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.adjust_size_sample, list)
    }
}