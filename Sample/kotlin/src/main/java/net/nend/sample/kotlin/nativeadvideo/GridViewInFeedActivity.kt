package net.nend.sample.kotlin.nativeadvideo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeVideoLoader
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewBinder
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder


class GridViewInFeedActivity : AppCompatActivity() {
    private val loadedAds = SparseArray<NendAdNativeVideo>()

    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_grid)

        val list = (1..99).map { i -> "item$i" }

        val gridView = findViewById<GridView>(R.id.grid)
        val adapter = NativeGridAdapter(this, 0, list)
        assert(gridView != null)
        gridView!!.adapter = adapter
    }

    internal inner class NativeGridAdapter(context: Context, resource: Int, list: List<String>) : ArrayAdapter<String>(context, resource, list) {

        private val videoLoader: NendAdNativeVideoLoader = NendAdNativeVideoLoader(
                context,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY,
                NendAdNativeVideo.VideoClickOption.LP).apply {
            setFillerImageNativeAd(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff")
        }
        private val videoBinder = MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.native_video_ad_mediaview)
                .titleId(R.id.native_video_ad_title)
                .advertiserId(R.id.native_video_ad_advertiser)
                .build()
        private val normalBinder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.native_video_ad_imageview)
                .titleId(R.id.native_video_ad_title)
                .prId(R.id.native_video_ad_advertiser, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .build()


        override fun getViewTypeCount() = ViewType.values().size

        override fun getItemViewType(position: Int) =
                if (position != 0 && position % 5 == 0) ViewType.AD.id else ViewType.NORMAL.id

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            var convertView = view
            val holder: ViewHolder
            when (getItemViewType(position)) {
                ViewType.AD.id -> {
                    val adHolder: MyNendAdViewHolder
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.native_video_ad_row_for_grid, parent, false)!!
                        adHolder = MyNendAdViewHolder(convertView, videoBinder)
                        convertView.tag = adHolder
                    } else {
                        adHolder = convertView.tag as MyNendAdViewHolder
                    }

                    val loadedAd = loadedAds.get(position)
                    if (loadedAd != null) {
                        adHolder.renderView(loadedAd, null)
                    } else {
                        videoLoader.loadAd(object : NendAdNativeVideoLoader.Callback {
                            override fun onSuccess(ad: NendAdNativeVideo) {
                                if (ad.hasVideo()) {
                                    adHolder.myItemView.findViewById<View>(R.id.native_video_ad_mediaview).visibility = View.VISIBLE
                                    adHolder.myItemView.findViewById<View>(R.id.native_video_ad_imageview).visibility = View.GONE
                                    loadedAds.put(position, ad)
                                    adHolder.renderView(ad, null)
                                } else {
                                    adHolder.myItemView.findViewById<View>(R.id.native_video_ad_mediaview).visibility = View.GONE
                                    adHolder.myItemView.findViewById<View>(R.id.native_video_ad_imageview).visibility = View.VISIBLE
                                    val nendAdNative = ad.fallbackAd
                                    nendAdNative.intoView(adHolder.myItemView, normalBinder)
                                }
                            }

                            override fun onFailure(error: Int) {}
                        })
                    }
                }
                else -> {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.native_grid_row, parent, false)!!
                        holder = ViewHolder()
                        holder.textView = convertView.findViewById(R.id.title)
                        holder.imageView = convertView.findViewById(R.id.thumbnail)
                        convertView.tag = holder
                    } else {
                        holder = convertView.tag as ViewHolder
                    }
                    holder.textView?.text = getItem(position)
                    holder.imageView?.setBackgroundColor(Color.LTGRAY)
                }
            }
            return convertView
        }

        internal inner class ViewHolder {
            var textView: TextView? = null
            var imageView: ImageView? = null
        }
    }
}
