package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import net.nend.android.NendAdView
import net.nend.sample.kotlin.R

class PageFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val list = arguments?.getStringArrayList("itemList")
        val position = arguments?.getInt("position")

        return (View.inflate(context, R.layout.page_list, null) as RelativeLayout).apply {
            (findViewById(R.id.page_title) as TextView).run {
                text = buildString {
                    append("PAGE")
                    append(position)
                }
            }
            (findViewById(R.id.page_list) as ListView).run {
                NendAdView(activity, 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f").run {
                    loadAd()
                    addHeaderView(this)
                }
                adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, list)
            }
        }
    }
}