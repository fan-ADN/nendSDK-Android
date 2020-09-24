package net.nend.sample.kotlin.nativead

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeListener
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.SimpleListActivity
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE

class NativeV2OnListActivity : SimpleListActivity() {

    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = NendAdNativeClient(this,
                NATIVE_SPOT_ID_SMALL_SQUARE, NATIVE_API_KEY_SMALL_SQUARE)
        val list = mutableListOf<Any>()

        for (i in 1..49) {
            val feed = NativeFeed()
            if (i == 10 || i == 20 || i == 30) {
                feed.type = ViewType.AD.id
                client.loadAd(object : NendAdNativeClient.Callback {
                    override fun onSuccess(nendAdNative: NendAdNative) {
                        feed.nendAdNative = nendAdNative
                    }

                    override fun onFailure(nendError: NendAdNativeClient.NendError) {}
                })
            } else {
                feed.type = ViewType.NORMAL.id
                feed.content = "【" + i + "行目】インフィード広告記事部分長いテキスト　インフィード広告記事部分長いテキスト　インフィード広告記事部分長いテキスト"
                feed.mediaName = "メディア名$i"
                feed.date = "1970/01/01"
            }
            list.add(feed)
        }

        val adapter = NativeListAdapter(applicationContext, 0, list)
        instantiateListAdapter(adapter)
    }

    private inner class NativeListAdapter(
            context: Context, resource: Int, private val objects: List<Any>) :
            ArrayAdapter<Any>(context, resource, objects) {

        private val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getViewTypeCount() = ViewType.values().size

        override fun getItemViewType(position: Int) = (objects[position] as NativeFeed).type

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = inflater.inflate(R.layout.native_v2_list_row, parent, false)
                holder = ViewHolder().apply {
                    title = view.findViewById(R.id.title) as TextView
                    contentText = view.findViewById(R.id.content) as TextView
                    smallText = view.findViewById(R.id.small_text) as TextView
                    thumbnail = view.findViewById(R.id.thumbnail) as ImageView
                    view.tag = this
                }
            } else {
                holder = convertView.tag as ViewHolder
                view = convertView
            }

            when (getItemViewType(position)) {
                ViewType.AD.id -> {
                    val adFeed = getItem(position) as NativeFeed?
                    adFeed?.let {
                        if (it.nendAdNative != null && it.adImage == null) {
                            it.nendAdNative?.downloadAdImage(object : NendAdNative.Callback {
                                override fun onSuccess(bitmap: Bitmap) {
                                    adFeed.adImage = bitmap
                                    runOnUiThread {
                                        holder.title?.text = it.nendAdNative?.titleText
                                        holder.contentText?.text = it.nendAdNative?.contentText
                                        holder.smallText?.text = NendAdNative.
                                                AdvertisingExplicitly.AD.text
                                        holder.thumbnail?.setImageBitmap(it.adImage)
                                        it.nendAdNative?.activate(view, holder.smallText)
                                        it.nendAdNative?.setNendAdNativeListener(object : NendAdNativeListener {
                                            override fun onImpression(ad: NendAdNative) {
                                                Log.i(TAG, "onImpression")
                                            }

                                            override fun onClickAd(ad: NendAdNative) {
                                                Log.i(TAG, "onClickAd")
                                            }

                                            override fun onClickInformation(ad: NendAdNative) {
                                                Log.i(TAG, "onClickInformation")
                                            }
                                        })
                                    }
                                }

                                override fun onFailure(error: Exception) {}
                            })
                        }
                        holder.smallText?.text = NendAdNative.AdvertisingExplicitly.AD.text
                        holder.thumbnail?.setImageBitmap(it.adImage)
                        adFeed.nendAdNative?.let { nendAd ->
                            holder.title?.text = nendAd.titleText
                            holder.contentText?.text = nendAd.contentText
                        }
                    }
                }
                else -> {
                    val feed = getItem(position) as NativeFeed?
                    feed?.let {
                        holder.contentText?.text = it.content
                        holder.smallText?.text = it.date
                        holder.title?.text = it.mediaName
                    }
                }
            }

            return view
        }

        inner class ViewHolder {
            var title: TextView? = null
            var contentText: TextView? = null
            var smallText: TextView? = null
            var thumbnail: ImageView? = null
        }
    }

    private inner class NativeFeed {
        var type: Int = 0
        var content: String? = null
        var date: String? = null
        var mediaName: String? = null
        var adImage: Bitmap? = null
        var nendAdNative: NendAdNative? = null
    }


    companion object {
        private val TAG = NativeV2OnListActivity::class.java.simpleName
    }

}
