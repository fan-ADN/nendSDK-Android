package net.nend.sample.kotlin.banner

import android.app.ListActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import net.nend.android.NendAdView
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_API_KEY_320_50
import net.nend.sample.kotlin.banner.BannerSampleActivity.Companion.BANNER_SPOT_ID_320_50

/**
 * ListViewに表示するサンプル
 */
class ListViewActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NendAdView(this, BANNER_SPOT_ID_320_50, BANNER_API_KEY_320_50).run {
            listView.addHeaderView(this)
            loadAd()
        }

        val list = (0..99).map { i -> "item$i" }
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
    }
}