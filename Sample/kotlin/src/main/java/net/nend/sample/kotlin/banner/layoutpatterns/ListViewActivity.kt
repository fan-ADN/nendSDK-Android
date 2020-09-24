package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import android.widget.ArrayAdapter
import net.nend.android.NendAdView
import net.nend.sample.kotlin.SimpleListActivity
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

/**
 * ListViewに表示するサンプル
 */
class ListViewActivity : SimpleListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = (0..99).map { i -> "item$i" }
        val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        instantiateListAdapter(listAdapter)
    }

    override fun onStart() {
        super.onStart()

        NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50).run {
            listView?.addHeaderView(this)
            loadAd()
        }
    }
}