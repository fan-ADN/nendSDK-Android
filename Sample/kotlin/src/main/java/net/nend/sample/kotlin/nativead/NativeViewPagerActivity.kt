package net.nend.sample.kotlin.nativead

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_LARGE_WIDE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_LARGE_WIDE

class NativeViewPagerActivity : AppCompatActivity(), NativePagerFragment.OnAdListener {

    private lateinit var binder: NendAdNativeViewBinder
    private lateinit var client: NendAdNativeClient
    // 広告を表示したポジションの一覧
    private val positionList = mutableListOf<Int>()
    // 表示したポジションと広告を紐付けて保持
    private val loadedAd = mutableMapOf<Int, NendAdNative>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_viewpager)

        val viewPager = findViewById(R.id.pager) as androidx.viewpager.widget.ViewPager
        viewPager.adapter = CustomPagerAdapter(supportFragmentManager)

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
    }

    override fun onAdRequest(view: View, position: Int) {
        if (loadedAd.containsKey(position)) {
            loadedAd[position]?.intoView(view, binder)
        } else {
            client.loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(TAG, "広告取得成功")
                    positionList.add(position)
                    nendAdNative.run {
                        intoView(view, binder)
                        setOnClickListener({ Log.i(TAG, "クリック") })
                        loadedAd[position] = this
                    }
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(TAG, "広告取得失敗: ${nendError.message} ")
                    // すでに取得済みの広告があればランダムで表示
                    if (!loadedAd.isEmpty()) {
                        positionList.shuffle()
                        loadedAd[positionList[0]]?.intoView(view, binder)
                    }
                }
            })
        }
    }

    inner class CustomPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            return NativePagerFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                }
            }
        }

        override fun getCount() = 5
    }

    companion object {
        private val TAG = NativeViewPagerActivity::class.java.simpleName
    }
}