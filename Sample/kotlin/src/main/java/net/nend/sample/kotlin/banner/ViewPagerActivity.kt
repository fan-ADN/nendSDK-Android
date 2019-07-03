package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import net.nend.sample.kotlin.R

/**
 * ViewPager内に表示するサンプル
 */
class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager: androidx.viewpager.widget.ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = androidx.viewpager.widget.ViewPager(this)
        setContentView(viewPager)

        val list = (1..10).map { i -> "page$i" }
        viewPager.adapter = CustomPagerAdapter(list)
    }

    inner class CustomPagerAdapter(private val list: List<String>) : androidx.viewpager.widget.PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val item = list[position]
            return (View.inflate(applicationContext, R.layout.page, null) as RelativeLayout).apply {
                (findViewById(R.id.page_title) as TextView).text = item
                container.addView(this)
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount() = list.size

        override fun isViewFromObject(view: View, `object`: Any) = view == `object` as RelativeLayout
    }
}