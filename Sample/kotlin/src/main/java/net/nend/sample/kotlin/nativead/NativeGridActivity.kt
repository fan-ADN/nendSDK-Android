package net.nend.sample.kotlin.nativead

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
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeListener
import net.nend.android.NendAdNativeViewBinder
import net.nend.android.NendAdNativeViewHolder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.databinding.NativeGridBinding
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE

class NativeGridActivity : AppCompatActivity() {

    private lateinit var binding: NativeGridBinding
    private enum class ViewType(val id: Int) {
        NORMAL(0),
        AD(1)
    }

    // 広告を表示したポジションの一覧
    private val positionList = mutableListOf<Int>()
    // 表示したポジションと広告を紐付けて保持
    private val loadedAd = mutableMapOf<Int, NendAdNative>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NativeGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = (1..99).map { i -> "item$i" }
        val adapter = NativeGridAdapter(this, 0, list)
        binding.grid.adapter = adapter
    }

    internal inner class NativeGridAdapter(context: Context, resource: Int, list: List<String>) :
            ArrayAdapter<String>(context, resource, list) {

        private val client: NendAdNativeClient = NendAdNativeClient(context,
                NATIVE_SPOT_ID_SMALL_SQUARE, NATIVE_API_KEY_SMALL_SQUARE)
        private val binder: NendAdNativeViewBinder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .titleId(R.id.ad_title)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .build()

        override fun getViewTypeCount() = ViewType.values().size

        override fun getItemViewType(position: Int) =
                if (position != 0 && position % 5 == 0) ViewType.AD.id else ViewType.NORMAL.id

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return when (getItemViewType(position)) {
                ViewType.AD.id -> {
                    generateAdView(position, convertView, parent)
                }
                else -> {
                    generateNormalView(position, convertView, parent)
                }
            }
        }

        private fun generateAdView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val adHolder: NendAdNativeViewHolder

            if (loadedAd.containsKey(position)) {
                adHolder = convertView?.tag as NendAdNativeViewHolder
                loadedAd[position]?.intoView(adHolder)
                view = convertView
                return view
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.native_ad_grid_row, parent, false)
                adHolder = binder.createViewHolder(view)
                view.tag = adHolder
            }

            client.loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功")
                    positionList.add(position)
                    nendAdNative.run {
                        intoView(adHolder)
                        setNendAdNativeListener(object : NendAdNativeListener {
                            override fun onImpression(ad: NendAdNative) {
                                Log.i(NATIVE_LOG_TAG, "onImpression")
                            }

                            override fun onClickAd(ad: NendAdNative) {
                                Log.i(NATIVE_LOG_TAG, "onClickAd")
                            }

                            override fun onClickInformation(ad: NendAdNative) {
                                Log.i(NATIVE_LOG_TAG, "onClickInformation")
                            }
                        })
                        loadedAd[position] = this
                    }
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                    // すでに取得済みの広告がればランダムで表示
                    if (loadedAd.isNotEmpty()) {
                        positionList.shuffle()
                        loadedAd[positionList[0]]?.intoView(adHolder)
                    }
                }
            })

            return view
        }

        private fun generateNormalView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.native_grid_row, parent, false)
                holder = ViewHolder().apply {
                    textView = view.findViewById(R.id.title) as TextView
                    imageView = view.findViewById(R.id.thumbnail) as ImageView
                    view.tag = this
                }
            } else {
                holder = convertView.tag as ViewHolder
                view = convertView
            }

            holder.textView?.text = getItem(position)
            holder.imageView?.setBackgroundColor(Color.LTGRAY)

            return view
        }

        internal inner class ViewHolder {
            var textView: TextView? = null
            var imageView: ImageView? = null
        }
    }
}