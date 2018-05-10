package net.nend.sample.kotlin.nativead

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.native_recycler.*
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE

class NativeRecyclerActivity : AppCompatActivity() {

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
        setContentView(R.layout.native_recycler)

        val list = (1..99).map { i -> "item$i" }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = NativeRecyclerAdapter(this, list)
    }

    internal inner class NativeRecyclerAdapter(context: Context, private val list: List<String>) :
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        private val client: NendAdNativeClient = NendAdNativeClient(context,
                NATIVE_SPOT_ID_SMALL_SQUARE, NATIVE_API_KEY_SMALL_SQUARE)
        private val binder: NendAdNativeViewBinder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .titleId(R.id.ad_title)
                .promotionNameId(R.id.ad_promotion_name)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
                .build()

        override fun getItemCount() = list.size

        override fun getItemViewType(position: Int) =
                if (position != 0 && position % 5 == 0) ViewType.AD.id else ViewType.NORMAL.id

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):
                RecyclerView.ViewHolder {
            val view: View
            var viewHolder: RecyclerView.ViewHolder

            when (viewType) {
                ViewType.AD.id -> {
                    view = layoutInflater.inflate(R.layout.native_ad_left_row, viewGroup, false)
                    viewHolder = binder.createRecyclerViewHolder(view)
                }
                else -> {
                    view = layoutInflater.inflate(R.layout.native_list_row, viewGroup, false)
                    viewHolder = ViewHolder(view)
                }
            }

            return viewHolder
        }

        @SuppressLint("RecyclerView")
        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when (getItemViewType(position)) {
                ViewType.NORMAL.id -> {
                    (viewHolder as ViewHolder).textView.text = list[position]
                    viewHolder.imageView.setBackgroundColor(Color.LTGRAY)
                }
                ViewType.AD.id -> {
                    if (loadedAd.containsKey(position)) {
                        loadedAd[position]?.intoView(viewHolder)
                    } else {
                        client.loadAd(object : NendAdNativeClient.Callback {
                            override fun onSuccess(nendAdNative: NendAdNative) {
                                Log.i(NATIVE_LOG_TAG, "広告取得成功")
                                positionList.add(position)
                                viewHolder.setIsRecyclable(false)
                                nendAdNative.run {
                                    intoView(viewHolder)
                                    setOnClickListener({
                                        Log.i(NATIVE_LOG_TAG, "クリック$position")
                                    })
                                    loadedAd[position] = this
                                }
                            }

                            override fun onFailure(nendError: NendAdNativeClient.NendError) {
                                Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message} ")
                                // すでに取得済みの広告をランダムで表示
                                if (!loadedAd.isEmpty()) {
                                    positionList.shuffle()
                                    loadedAd[positionList[0]]?.intoView(viewHolder)
                                }
                            }
                        })
                    }
                }
            }
        }

        internal inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            var textView: TextView = v.findViewById(R.id.title) as TextView
            var imageView: ImageView = v.findViewById(R.id.thumbnail) as ImageView
        }
    }
}