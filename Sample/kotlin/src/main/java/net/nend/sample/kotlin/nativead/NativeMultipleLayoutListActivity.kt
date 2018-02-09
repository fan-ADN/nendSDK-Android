package net.nend.sample.kotlin.nativead

import android.app.ListActivity
import android.content.Context
import android.graphics.Color
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
import net.nend.android.NendAdNativeViewBinder
import net.nend.android.NendAdNativeViewHolder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE

class NativeMultipleLayoutListActivity : ListActivity() {

    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD1(1),
        AD2(2)
    }

    // 広告を表示したポジションの一覧
    private val positionList = mutableListOf<Int>()
    // 表示したポジションと広告を紐付けて保持
    private val loadedAd = mutableMapOf<Int, NendAdNative>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = (1..99).map { i -> "item$i" }
        listAdapter = NativeListAdapter(this, 0, list)
    }

    internal inner class NativeListAdapter(context: Context, resource: Int, list: List<String>) :
            ArrayAdapter<String>(context, resource, list) {

        private val client: NendAdNativeClient = NendAdNativeClient(context,
                NATIVE_SPOT_ID_SMALL_SQUARE, NATIVE_API_KEY_SMALL_SQUARE)
        private val binder: NendAdNativeViewBinder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .titleId(R.id.ad_title)
                .promotionNameId(R.id.ad_promotion_name)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .build()

        override fun getViewTypeCount() = ViewType.values().size

        override fun getItemViewType(position: Int): Int {
            return if (position != 0 && position % 5 == 0) {
                if (position % 10 == 0) ViewType.AD2.id else ViewType.AD1.id
            } else ViewType.NORMAL.id
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder
            val adHolder: NendAdNativeViewHolder

            when (getItemViewType(position)) {
                ViewType.AD1.id -> {
                    if (loadedAd.containsKey(position)) {
                        adHolder = convertView?.tag as NendAdNativeViewHolder
                        loadedAd[position]?.intoView(adHolder)
                        view = convertView
                    } else {
                        view = LayoutInflater
                                .from(context).inflate(R.layout.native_ad_left_row, parent, false)
                        loadAndSetAd(view, position)
                    }
                }
                ViewType.AD2.id -> {
                    if (loadedAd.containsKey(position)) {
                        adHolder = convertView?.tag as NendAdNativeViewHolder
                        loadedAd[position]?.intoView(adHolder)
                        view = convertView
                    } else {
                        view = LayoutInflater
                                .from(context).inflate(R.layout.native_ad_right_row, parent, false)
                        loadAndSetAd(view, position)
                    }
                }
                else -> {
                    if (convertView == null) {
                        view = LayoutInflater
                                .from(context).inflate(R.layout.native_list_row, parent, false)
                        holder = ViewHolder().apply {
                            textView = view.findViewById(R.id.title) as TextView
                            imageView = view.findViewById(R.id.thumbnail) as ImageView
                            view.tag = this
                        }
                    } else {
                        view = convertView
                        holder = view.tag as ViewHolder
                    }

                    holder.textView?.text = getItem(position)
                    holder.imageView?.setBackgroundColor(Color.LTGRAY)
                }
            }

            return view
        }

        private fun loadAndSetAd(convertView: View, position: Int) {
            val adHolder = binder.createViewHolder(convertView)
            convertView.tag = adHolder
            client.loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功")
                    nendAdNative.run {
                        positionList.add(position)
                        intoView(adHolder)
                        loadedAd[position] = this
                    }
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                    // すでに取得済みの広告があればランダムで表示
                    if (!loadedAd.isEmpty()) {
                        positionList.shuffle()
                        loadedAd[positionList[0]]?.intoView(adHolder)
                    }
                }
            })
        }

        internal inner class ViewHolder {
            var textView: TextView? = null
            var imageView: ImageView? = null
        }
    }
}