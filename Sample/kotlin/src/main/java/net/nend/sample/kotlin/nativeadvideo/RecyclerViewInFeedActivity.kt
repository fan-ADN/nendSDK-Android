package net.nend.sample.kotlin.nativeadvideo

import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeVideoLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.databinding.NativeListRowBinding
import net.nend.sample.kotlin.databinding.NativeRecyclerBinding
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewBinder
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder

class RecyclerViewInFeedActivity : AppCompatActivity() {

    private lateinit var binding: NativeRecyclerBinding
    private val loadedAds = SparseArray<NendAdNativeVideo>()

    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD(1);

        companion object {
            fun from(id: Int): ViewType? = when (id) {
                0 -> NORMAL
                1 -> AD
                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NativeRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = (1..99).map { i -> "item$i" }
        binding.run {
            recycler.run {
                layoutManager = LinearLayoutManager(this@RecyclerViewInFeedActivity)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            val manager = recyclerView.layoutManager as LinearLayoutManager
                            val firstPosition = manager.findFirstVisibleItemPosition()
                            val lastPosition = manager.findLastVisibleItemPosition()

                            (firstPosition..lastPosition).forEach { index ->
                                val holder = recyclerView.findViewHolderForAdapterPosition(index)
                                if (holder is MyNendAdViewHolder) {
                                    adapter?.onBindViewHolder(holder, index)
                                }
                            }
                        }
                    }
                })
                adapter = NativeRecyclerAdapter(list)
            }
        }
    }

    internal inner class NativeRecyclerAdapter(
        private val objects: List<String>
    ) : ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback) {
        private lateinit var loader: NendAdNativeVideoLoader
        private val videoBinder = MyNendAdViewBinder.Builder()
            .mediaViewId(R.id.native_video_ad_for_feed_video)
            .titleId(R.id.native_video_ad_for_feed_title)
            .advertiserId(R.id.native_video_ad_for_feed_advertiser_name)
            .build()

        override fun getItemCount() = objects.size

        override fun getItemViewType(position: Int) = if (position != 0 && position % 5 == 0) {
            ViewType.AD.id
        } else {
            ViewType.NORMAL.id
        }

        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup.context)
            loader = NendAdNativeVideoLoader(
                viewGroup.context,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY
            )

            val viewHolder = when (ViewType.from(viewType)) {
                ViewType.AD -> {
                    MyNendAdViewHolder(
                        layoutInflater.inflate(
                            R.layout.native_video_ad_row_for_feed,
                            viewGroup,
                            false
                        ),
                        videoBinder
                    )
                }
                ViewType.NORMAL -> {
                    val binding = NativeListRowBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    )
                    ViewHolder(binding)
                }
                else -> null
            } ?: throw Exception("Unexpected ViewType")

            return viewHolder
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (viewHolder) {
                is ViewHolder -> {
                    viewHolder.bind(position)
                }
                is MyNendAdViewHolder -> {
                    val loadedAd = loadedAds.get(position)
                    if (loadedAd != null) {
                        setNativeVideoAdAtViewHolder(loadedAd, viewHolder)
                    } else {
                        loader.loadAd(object : NendAdNativeVideoLoader.Callback {
                            override fun onSuccess(ad: NendAdNativeVideo) {
                                runOnUiThread {
                                    loadedAds.put(viewHolder.adapterPosition, ad)
                                    setNativeVideoAdAtViewHolder(
                                        ad,
                                        viewHolder
                                    )
                                }
                            }

                            override fun onFailure(errorCode: Int) {
                            }
                        })
                    }
                }
            }
        }

        private fun setNativeVideoAdAtViewHolder(
            nativeVideo: NendAdNativeVideo,
            holder: MyNendAdViewHolder
        ) {
            holder.logoImageView?.setImageBitmap(nativeVideo.logoImageBitmap)
            holder.titleTextView?.text = nativeVideo.titleText
            holder.contentTextView?.text = nativeVideo.descriptionText
            holder.advertiserNameTextView?.text = nativeVideo.advertiserName
            holder.prTextView?.text = NendAdNative.AdvertisingExplicitly.SPONSORED.text
            holder.actionTextView?.text = nativeVideo.callToActionText

            nativeVideo.isMutePlayingFullscreen = true

            val parent = holder.myItemView.parent
            if (parent is RecyclerView) {
                videoBinder.renderView(holder, nativeVideo, null)
            }
        }

        internal inner class ViewHolder(
            private val binding: NativeListRowBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int) {
                binding.run {
                    title.text = objects[position]
                    thumbnail.setBackgroundColor(Color.LTGRAY)

                    executePendingBindings()
                }
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}
