package net.nend.sample.kotlin.nativead

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeListener
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

        val viewPager = findViewById<ViewPager>(R.id.pager)
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
                        setNendAdNativeListener(object : NendAdNativeListener {
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
                        loadedAd[position] = this
                    }
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(TAG, "広告取得失敗: ${nendError.message} ")
                    // すでに取得済みの広告があればランダムで表示
                    if (loadedAd.isNotEmpty()) {
                        positionList.shuffle()
                        loadedAd[positionList[0]]?.intoView(view, binder)
                    }
                }
            })
        }
    }

    inner class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
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