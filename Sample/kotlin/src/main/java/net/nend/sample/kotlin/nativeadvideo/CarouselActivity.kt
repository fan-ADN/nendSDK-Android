package net.nend.sample.kotlin.nativeadvideo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeVideoListener
import net.nend.android.NendAdNativeVideoLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeCarouselPagerFragment
import net.nend.sample.kotlin.nativead.NativeCarouselViewPager
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewBinder
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder
import java.text.SimpleDateFormat
import java.util.*

class CarouselActivity : AppCompatActivity(), NativeCarouselPagerFragment.OnAdListener {

    private enum class ViewType(val id: Int) {
        FEED(0),
        AD(1)
    }

    private lateinit var binder: MyNendAdViewBinder
    private lateinit var loader: NendAdNativeVideoLoader
    private val loadedAds = SparseArray<NendAdNativeVideo>()

    private var menuPosition = 0
    private var autoCarouselRunnable: Runnable? = null
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_carousel)

        // フィード用リスト
        val list = (1..19).map { i -> "sample name$i" }

        // 親のRecyclerView
        val parentRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.carousel_recycler_parent)!!
        parentRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        parentRecyclerView.adapter = CarouselAdapter(this, list)

        // セルのスクロールイン・スクロールアウトでオートカルーセルを操作
        parentRecyclerView.addOnChildAttachStateChangeListener(object : androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (view.tag == "CAROUSEL" && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler.postDelayed(autoCarouselRunnable, INTERVAL.toLong())
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (view.tag == "CAROUSEL" && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler.removeCallbacks(autoCarouselRunnable)
                }
            }
        })

        binder = MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.ad_mediaview)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .advertiserId(R.id.ad_advertiser)
                .actionId(R.id.ad_action)
                .build()
        loader = NendAdNativeVideoLoader(this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY)

        // オートカルーセル用
        menuPosition = intent.getIntExtra("type", 0)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
            if (!hasFocus) {
                handler.removeCallbacks(autoCarouselRunnable)
            } else {
                handler.postDelayed(autoCarouselRunnable, INTERVAL.toLong())
            }
        }
    }

    override fun onAdRequest(view: View, position: Int) {
        val loadedAd = loadedAds.get(position)
        val holder = MyNendAdViewHolder(view, binder)
        if (loadedAd != null) {
            holder.renderView(loadedAd, null)
        } else {
            loader.loadAd(object : NendAdNativeVideoLoader.Callback {
                override fun onSuccess(ad: NendAdNativeVideo) {
                    Log.i(TAG, "広告取得成功")
                    loadedAds.put(position, ad)
                    ad.listener = object : NendAdNativeVideoListener {
                        override fun onImpression(nendAdNativeVideo: NendAdNativeVideo) {}

                        override fun onClickAd(nendAdNativeVideo: NendAdNativeVideo) {
                            Log.i(TAG, "クリック")
                        }

                        override fun onClickInformation(nendAdNativeVideo: NendAdNativeVideo) {}
                    }
                    holder.renderView(ad, null)
                }

                override fun onFailure(errorCode: Int) {
                    Log.i(TAG, "広告取得失敗: $errorCode")
                }
            })
        }
    }

    internal inner class CarouselAdapter(context: Context, private val list: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
            val view: View
            val viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder
            when (viewType) {
                ViewType.AD.id -> {
                    view = layoutInflater.inflate(R.layout.native_carousel_viewpager, parent, false).apply {
                        tag = "CAROUSEL"
                    }
                    viewHolder = AdHolder(view)
                }
                else -> {
                    view = layoutInflater.inflate(R.layout.native_carousel_card_feed, parent, false)
                    viewHolder = FeedHolder(view)
                }
            }
            return viewHolder
        }

        override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
            when (getItemViewType(position)) {
                ViewType.FEED.id -> {
                    val date = Date()
                    // 表示形式を設定
                    val currentDate = SimpleDateFormat("yyyy'年'MM'月'dd'日'　kk'時'mm'分'ss'秒'", Locale.JAPAN)
                    val longText = resources.getString(R.string.carousel_longtext)
                    if (viewHolder is FeedHolder) {
                        viewHolder.textName.text = list[position]
                        viewHolder.textDate.text = currentDate.format(date)
                        viewHolder.textComment.text = longText
                    }
                }
            }
        }

        override fun getItemViewType(position: Int) =
                if (position == 3) ViewType.AD.id else ViewType.FEED.id

        override fun getItemCount() = list.size

        internal inner class FeedHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
            var textName: TextView = itemView.findViewById(R.id.carousel_feed_name)
            var textDate: TextView = itemView.findViewById(R.id.carousel_feed_date)
            var textComment: TextView = itemView.findViewById(R.id.carousel_feed_comment)
        }

        // 広告用ホルダー
        internal inner class AdHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
            var viewPager: NativeCarouselViewPager = itemView.findViewById(R.id.carousel_pager)

            init {
                viewPager.run {
                    adapter = CustomPagerAdapter(supportFragmentManager)
                    pageMargin = resources.displayMetrics.widthPixels / -10
                }

                // オートカルーセル
                if (menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    autoCarouselRunnable = object : Runnable {
                        override fun run() {
                            var nextItem = viewPager.currentItem + 1
                            if (nextItem == 5) {
                                nextItem = 0
                            }
                            viewPager.setCurrentItem(nextItem, true)
                            handler.postDelayed(this, INTERVAL.toLong())
                        }
                    }
                }
            }
        }
    }

    // カルーセル部分ViewPager用Adapter
    inner class CustomPagerAdapter internal constructor(fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int) =
                NativeCarouselPagerFragment.newInstance(position, R.layout.native_video_carousel_fragment)

        override fun getCount() = 5
    }


    companion object {
        private const val AUTO_CAROUSEL_MENU_POSITION = 9  // native ad sample menu position
        private const val INTERVAL = 3000
        private const val TAG = ExamplesActivity.LOG_TAG
    }
}
