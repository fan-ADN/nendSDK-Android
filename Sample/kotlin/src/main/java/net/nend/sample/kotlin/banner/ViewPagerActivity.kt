package net.nend.sample.kotlin.banner

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import net.nend.sample.kotlin.R

/**
 * ViewPager内に表示するサンプル
 */
class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = ViewPager(this)
        setContentView(viewPager)

        val list = (1..10).map { i -> "page$i" }
        viewPager.adapter = CustomPagerAdapter(list)
    }

    inner class CustomPagerAdapter(private val list: List<String>) : PagerAdapter() {

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