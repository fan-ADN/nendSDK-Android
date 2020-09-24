package net.nend.sample.kotlin.banner.noxml

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class NoXmlLayoutActivity : SimpleListActivity() {

    private val list: ArrayList<Class<*>?> = object : ArrayList<Class<*>?>() {
        init {
            add(NoXmlLinearActivity::class.java)
            add(NoXmlRelativeActivity::class.java)
            add(NoXmlWithListenerActivity::class.java)
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.no_xml, list)
    }
}