package net.nend.sample.kotlin.nativead

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE
import java.text.SimpleDateFormat
import java.util.*

class NativeCarouselAdActivity : AppCompatActivity(), NativeCarouselPagerFragment.OnAdListener {

    private enum class ViewType(val id: Int) {
        FEED(0),
        AD(1)
    }

    private lateinit var binder: NendAdNativeViewBinder
    private lateinit var client: NendAdNativeClient
    private val loadedAd = mutableMapOf<Int, NendAdNative>()

    private var menuPosition = 0
    private lateinit var autoCarouselRunnable: Runnable
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_carousel)

        // フィード用リスト
        val list = (1..19).map { i -> "sample name$i" }

        // 親のRecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.carousel_recycler_parent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CarouselAdapter(this, list)
        // セルのスクロールイン・スクロールアウトでオートカルーセルを操作
        recyclerView.addOnChildAttachStateChangeListener(
                object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (view.tag != null &&
                        view.tag == "CAROUSEL" && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler?.postDelayed(autoCarouselRunnable, INTERVAL.toLong())
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (view.tag != null &&
                        view.tag == "CAROUSEL" && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler?.removeCallbacks(autoCarouselRunnable)
                }
            }
        })

        binder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .logoImageId(R.id.logo_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .promotionUrlId(R.id.ad_promotion_url)
                .promotionNameId(R.id.ad_promotion_name)
                .actionId(R.id.ad_action)
                .build()
        client = NendAdNativeClient(this, NATIVE_SPOT_ID_LARGE_WIDE, NATIVE_API_KEY_LARGE_WIDE)

        menuPosition = intent.getIntExtra("type", 0)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (handler != null && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
            if (!hasFocus) {
                handler?.removeCallbacks(autoCarouselRunnable)
            } else {
                handler?.postDelayed(autoCarouselRunnable, INTERVAL.toLong())
            }
        }
    }

    override fun onAdRequest(view: View, position: Int) {
        if (loadedAd.containsKey(position)) {
            loadedAd[position]?.intoView(view, binder)
        } else {
            client.loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功")
                    nendAdNative.run {
                        intoView(view, binder)
                        setOnClickListener { Log.i(NATIVE_LOG_TAG, "クリック") }
                        loadedAd[position] = this
                    }
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                }
            })
        }
    }

    // リスト全体のアダプター
    internal inner class CarouselAdapter(context: Context, private val list: List<String>) :
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View
            val viewHolder: RecyclerView.ViewHolder

            when (viewType) {
                ViewType.AD.id -> {
                    view = layoutInflater.inflate(R.layout.native_carousel_viewpager, parent, false)
                    view.tag = "CAROUSEL"
                    viewHolder = AdHolder(view)
                }
                else -> {
                    view = layoutInflater.inflate(R.layout.native_carousel_card_feed, parent, false)
                    viewHolder = FeedHolder(view)
                }
            }

            return viewHolder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (getItemViewType(position)) {
                ViewType.FEED.id -> {
                    val date = Date()
                    // 表示形式を設定
                    val currentDate = SimpleDateFormat(
                            "yyyy'年'MM'月'dd'日'　kk'時'mm'分'ss'秒'", Locale.JAPAN)
                    val longText = resources.getString(R.string.carousel_longtext)
                    (holder as FeedHolder).run {
                        textName.text = list[position]
                        textDate.text = currentDate.format(date)
                        textComment.text = longText
                    }
                }
            }
        }

        override fun getItemViewType(position: Int) =
                if (position == 3) ViewType.AD.id else ViewType.FEED.id

        override fun getItemCount() = list.size

        internal inner class FeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textName: TextView = itemView.findViewById(R.id.carousel_feed_name) as TextView
            var textDate: TextView = itemView.findViewById(R.id.carousel_feed_date) as TextView
            var textComment: TextView = itemView.findViewById(R.id.carousel_feed_comment) as TextView
        }

        // 広告用ホルダー
        internal inner class AdHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var viewPager: NativeCarouselViewPager =
                    itemView.findViewById(R.id.carousel_pager) as NativeCarouselViewPager

            init {
                viewPager.adapter = CustomPagerAdapter(supportFragmentManager)
                viewPager.pageMargin = resources.displayMetrics.widthPixels / -10
                // オートカルーセル
                if (menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler = Handler()
                    autoCarouselRunnable = object : Runnable {
                        override fun run() {
                            var nextItem = viewPager.currentItem + 1
                            if (nextItem == 5) {
                                nextItem = 0
                            }
                            viewPager.setCurrentItem(nextItem, true)
                            handler?.postDelayed(this, INTERVAL.toLong())
                        }
                    }
                }
            }
        }
    }

    // カルーセル部分ViewPager用Adapter
    inner class CustomPagerAdapter(fragmentManager: FragmentManager) :
            androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment =
                NativeCarouselPagerFragment.newInstance(position, R.layout.native_carousel_fragment)

        override fun getCount() = 5
    }

    companion object {
        private const val AUTO_CAROUSEL_MENU_POSITION = 9  // native ad sample menu position
        private const val INTERVAL = 3000
    }
}