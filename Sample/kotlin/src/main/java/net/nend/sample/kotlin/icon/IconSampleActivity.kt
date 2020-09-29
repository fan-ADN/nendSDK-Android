package net.nend.sample.kotlin.icon

import android.os.Bundle
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import java.util.*

class IconSampleActivity : SimpleListActivity() {

    private val list: ArrayList<Class<*>?> = object : ArrayList<Class<*>?>() {
        init {
            add(IconActivity::class.java)
            add(IconResourceActivity::class.java)
            add(IconNoXmlActivity::class.java)
            add(IconAttachAndDetachActivity::class.java)
            add(IconSpaceActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateMenuListFragment(R.layout.activity_icom_sample, list)
    }

    companion object {
        const val ICON_SPOT_ID = 101282
        const val ICON_API_KEY = "0c734134519f25412ae9a9bff94783b81048ffbe"
    }
}