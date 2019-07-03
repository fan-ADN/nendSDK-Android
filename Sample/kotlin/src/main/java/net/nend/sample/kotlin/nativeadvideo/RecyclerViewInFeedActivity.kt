package net.nend.sample.kotlin.nativeadvideo

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeVideoLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewBinder
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder

class RecyclerViewInFeedActivity : AppCompatActivity() {

    private lateinit var nativeRecyclerAdapter: NativeRecyclerAdapter
    private val loadedAds = SparseArray<NendAdNativeVideo>()

    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_recycler)

        val list = (1..99).map { i -> "item$i" }
        nativeRecyclerAdapter = NativeRecyclerAdapter(this, list)

        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler).run {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@RecyclerViewInFeedActivity)
            addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE) {
                        val manager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
                        val firstPosition = manager.findFirstVisibleItemPosition()
                        val lastPosition = manager.findLastVisibleItemPosition()

                        for (index in firstPosition until lastPosition) {
                            val holder = recyclerView.findViewHolderForAdapterPosition(index)
                            if (holder is MyNendAdViewHolder) {
                                adapter!!.onBindViewHolder(holder, index)
                            }
                        }
                    }
                }
            })
            adapter = nativeRecyclerAdapter
        }
    }

    internal inner class NativeRecyclerAdapter(private val context: Context, private val objects: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

        private val layoutInflater = LayoutInflater.from(context)
        private val loader = NendAdNativeVideoLoader(context,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY)
        private val videoBinder = MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.native_video_ad_for_feed_video)
                .titleId(R.id.native_video_ad_for_feed_title)
                .advertiserId(R.id.native_video_ad_for_feed_advertiser_name)
                .build()

        override fun getItemCount() = objects.size

        override fun getItemViewType(position: Int) =
                if (position != 0 && position % 5 == 0) ViewType.AD.id else ViewType.NORMAL.id

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
            val view: View
            val viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder
            when (viewType) {
                ViewType.AD.id -> {
                    view = layoutInflater.inflate(R.layout.native_video_ad_row_for_feed, viewGroup, false)
                    viewHolder = MyNendAdViewHolder(view, videoBinder)
                }
                else -> {
                    view = layoutInflater.inflate(R.layout.native_list_row, viewGroup, false)
                    viewHolder = ViewHolder(view)
                }
            }
            return viewHolder
        }

        override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
            when (getItemViewType(position)) {
                ViewType.AD.id -> {
                    val loadedAd = loadedAds.get(position)
                    if (loadedAd != null) {
                        setNativeVideoAdAtViewHolder(loadedAd, viewHolder as MyNendAdViewHolder)
                    } else {
                        loader.loadAd(object : NendAdNativeVideoLoader.Callback {
                            override fun onSuccess(ad: NendAdNativeVideo) {
                                (context as Activity).runOnUiThread {
                                    loadedAds.put(viewHolder.adapterPosition, ad)
                                    setNativeVideoAdAtViewHolder(ad, viewHolder as MyNendAdViewHolder)
                                }
                            }

                            override fun onFailure(errorCode: Int) {

                            }
                        })
                    }
                }
                else -> {
                    (viewHolder as ViewHolder).textView.text = objects[position]
                    viewHolder.imageView.setBackgroundColor(Color.LTGRAY)
                }
            }
        }

        private fun setNativeVideoAdAtViewHolder(nativeVideo: NendAdNativeVideo, holder: MyNendAdViewHolder) {
            holder.logoImageView?.setImageBitmap(nativeVideo.logoImageBitmap)
            holder.titleTextView?.text = nativeVideo.titleText
            holder.contentTextView?.text = nativeVideo.descriptionText
            holder.advertiserNameTextView?.text = nativeVideo.advertiserName
            holder.prTextView?.text = NendAdNative.AdvertisingExplicitly.SPONSORED.text
            holder.actionTextView?.text = nativeVideo.callToActionText

            nativeVideo.isMutePlayingFullscreen = true

            val parent = holder.myItemView.parent
            if (parent is androidx.recyclerview.widget.RecyclerView) {
                videoBinder.renderView(holder, nativeVideo, null)
            }
        }

        internal inner class ViewHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {

            var textView: TextView = v.findViewById(R.id.title)
            var imageView: ImageView = v.findViewById(R.id.thumbnail)
        }
    }
}
