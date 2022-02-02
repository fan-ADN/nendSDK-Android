package net.nend.sample.kotlin.nativead

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeListener
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.databinding.NativeListRowBinding
import net.nend.sample.kotlin.databinding.NativeRecyclerBinding
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE
import net.nend.sample.kotlin.nativeadvideo.utilities.MyNendAdViewHolder

class NativeRecyclerActivity : AppCompatActivity() {

    private lateinit var binding: NativeRecyclerBinding

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

    // 広告を表示したポジションの一覧
    private val positionList = mutableListOf<Int>()

    // 表示したポジションと広告を紐付けて保持
    private val loadedAd = mutableMapOf<Int, NendAdNative>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NativeRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = (1..99).map { i -> "item$i" }
        binding.run {
            recycler.run {
                layoutManager = LinearLayoutManager(this@NativeRecyclerActivity)
                adapter = NativeRecyclerAdapter(list)
            }
        }
    }

    internal inner class NativeRecyclerAdapter(
        private val list: List<String>
    ) : ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback) {
        private lateinit var client: NendAdNativeClient
        private val binder: NendAdNativeViewBinder = NendAdNativeViewBinder.Builder()
            .adImageId(R.id.ad_image)
            .titleId(R.id.ad_title)
            .promotionNameId(R.id.ad_promotion_name)
            .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
            .build()

        override fun getItemCount() = list.size

        override fun getItemViewType(position: Int) = if (position != 0 && position % 5 == 0) {
            ViewType.AD.id
        } else {
            ViewType.NORMAL.id
        }

        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
            client = NendAdNativeClient(
                viewGroup.context,
                NATIVE_SPOT_ID_SMALL_SQUARE,
                NATIVE_API_KEY_SMALL_SQUARE
            )

            val viewHolder = when (ViewType.from(viewType)) {
                ViewType.AD -> {
                    MyNendAdViewHolder(
                        layoutInflater.inflate(
                            R.layout.native_ad_left_row,
                            viewGroup,
                            false
                        ),
                        binder
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
                    viewHolder.bind(viewHolder.layoutPosition)
                }
                is MyNendAdViewHolder -> {
                    if (loadedAd.containsKey(viewHolder.layoutPosition)) {
                        loadedAd[viewHolder.layoutPosition]?.run {
                            setNativeAdViewHolder(this, viewHolder)
                        }
                    } else {
                        client.loadAd(object : NendAdNativeClient.Callback {
                            override fun onSuccess(nendAdNative: NendAdNative) {
                                Log.i(NATIVE_LOG_TAG, "広告取得成功")
                                positionList.add(viewHolder.layoutPosition)
                                viewHolder.setIsRecyclable(false)
                                nendAdNative.run {
                                    setNativeAdViewHolder(nendAdNative, viewHolder)
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
                                    loadedAd[viewHolder.layoutPosition] = this
                                }
                            }

                            override fun onFailure(nendError: NendAdNativeClient.NendError) {
                                Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message} ")
                                // すでに取得済みの広告をランダムで表示
                                if (loadedAd.isNotEmpty()) {
                                    positionList.shuffle()
                                    loadedAd[positionList[0]]?.run {
                                        setNativeAdViewHolder(this, viewHolder)
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }

        private fun setNativeAdViewHolder(
            nativeAd: NendAdNative,
            holder: MyNendAdViewHolder,
        ) {
            nativeAd.intoView(
                holder.myItemView,
                holder.normalBinder,
            )
        }

        internal inner class ViewHolder(
            private val binding: NativeListRowBinding,
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int) {
                binding.run {
                    title.text = list[position]
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