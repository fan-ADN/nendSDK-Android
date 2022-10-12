package net.nend.sample.kotlin.banner.noxml

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class NoXmlLayoutActivity : SimpleListActivity() {

    private val list: ArrayList<String> = object : ArrayList<String>() {
        init {
            add(NoXmlLinearActivity::class.java.name)
            add(NoXmlRelativeActivity::class.java.name)
            add(NoXmlWithListenerActivity::class.java.name)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.no_xml, list)
    }
}