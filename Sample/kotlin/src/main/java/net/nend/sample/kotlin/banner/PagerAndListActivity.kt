package net.nend.sample.kotlin.banner

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import net.nend.sample.kotlin.R

/**
 * ViewPager内のListViewに表示するサンプル
 */
class PagerAndListActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = ViewPager(this)
        viewPager.id = R.id.pager
        setContentView(viewPager)

        val pageList = (1..10).map { i -> "page$i" }
        val itemList = (1..50).map { i -> "item$i" }
        viewPager.adapter = CustomPagerAdapter(supportFragmentManager, pageList, itemList)
    }

    inner class CustomPagerAdapter(
            fragmentManager: FragmentManager,
            private val pageList: List<String>,
            private val itemList: List<String>) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            val args = Bundle().apply {
                putStringArrayList("itemList", ArrayList(itemList))
                putInt("position", position)
            }
            return Fragment.instantiate(applicationContext, PageFragment::class.java.name, args)
        }

        override fun getCount() = pageList.size
    }
}