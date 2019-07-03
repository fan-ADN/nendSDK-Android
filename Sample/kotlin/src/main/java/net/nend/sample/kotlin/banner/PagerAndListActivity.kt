package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import net.nend.sample.kotlin.R

/**
 * ViewPager内のListViewに表示するサンプル
 */
class PagerAndListActivity : AppCompatActivity() {

    private lateinit var viewPager: androidx.viewpager.widget.ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = androidx.viewpager.widget.ViewPager(this)
        viewPager.id = R.id.pager
        setContentView(viewPager)

        val pageList = (1..10).map { i -> "page$i" }
        val itemList = (1..50).map { i -> "item$i" }
        viewPager.adapter = CustomPagerAdapter(supportFragmentManager, pageList, itemList)
    }

    inner class CustomPagerAdapter(
            fragmentManager: androidx.fragment.app.FragmentManager,
            private val pageList: List<String>,
            private val itemList: List<String>) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            val args = Bundle().apply {
                putStringArrayList("itemList", ArrayList(itemList))
                putInt("position", position)
            }
            return androidx.fragment.app.Fragment.instantiate(applicationContext, PageFragment::class.java.name, args)
        }

        override fun getCount() = pageList.size
    }
}