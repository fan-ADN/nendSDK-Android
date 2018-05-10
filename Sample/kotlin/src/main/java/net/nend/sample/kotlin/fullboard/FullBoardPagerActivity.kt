package net.nend.sample.kotlin.fullboard

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_full_board_pager.*
import net.nend.android.NendAdFullBoard
import net.nend.android.NendAdFullBoardLoader
import net.nend.android.NendAdFullBoardView
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_API_KEY
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_LOG_TAG
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_SPOT_ID
import java.util.concurrent.CountDownLatch

class FullBoardPagerActivity : AppCompatActivity(),
        LoaderManager.LoaderCallbacks<List<NendAdFullBoard>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_board_pager)

        supportLoaderManager.initLoader(0, Bundle(), this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Handler().post {
            // Update the ad orientation.
            pager.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<NendAdFullBoard>> {
        return AdLoader(applicationContext)
    }

    override fun onLoadFinished(loader: Loader<List<NendAdFullBoard>>, data: List<NendAdFullBoard>) {

        val pages = (0..4).map { ContentPage() }.toMutableList<Page>()
        if (AD_COUNT == data.size) {
            pages.add(2, AdPage(data[0]))
            pages.add(4, AdPage(data[1]))
        } else {
            Log.d(FULLBOARD_LOG_TAG, "Couldn't obtain two ads.")
        }
        pager.adapter = Adapter(supportFragmentManager, pages)
    }

    override fun onLoaderReset(loader: Loader<List<NendAdFullBoard>>) {}

    internal interface Page {
        val fragment: Fragment
    }

    class ContentFragment : Fragment() {
        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return TextView(activity).apply {
                text = "Content"
                gravity = Gravity.CENTER
            }
        }
    }

    class AdFragment : Fragment(), NendAdFullBoardView.FullBoardAdClickListener {
        private var fullBoardAd: NendAdFullBoard? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return if (fullBoardAd != null) {
                NendAdFullBoardView.Builder.with(activity, fullBoardAd)
                        .adClickListener(this)
                        .build()
            } else {
                null
            }
        }

        internal fun setAd(ad: NendAdFullBoard) {
            fullBoardAd = ad
        }

        override fun onClickAd(ad: NendAdFullBoard) {
            Log.d(FULLBOARD_LOG_TAG, "ClickAd")
        }
    }

    private class AdLoader internal constructor(context: Context) :
            AsyncTaskLoader<List<NendAdFullBoard>>(context) {

        private var ads: List<NendAdFullBoard>? = null
        private val loader: NendAdFullBoardLoader = NendAdFullBoardLoader(context,
                FULLBOARD_SPOT_ID, FULLBOARD_API_KEY)

        override fun loadInBackground(): List<NendAdFullBoard> {
            val latch = CountDownLatch(AD_COUNT)
            val ads = mutableListOf<NendAdFullBoard>()

            for (i in 0 until AD_COUNT) {
                loader.loadAd(object : NendAdFullBoardLoader.Callback {
                    override fun onSuccess(ad: NendAdFullBoard) {
                        ads.add(ad)
                        latch.countDown()
                    }

                    override fun onFailure(error: NendAdFullBoardLoader.FullBoardAdError) {
                        latch.countDown()
                    }
                })
            }
            try {
                latch.await()
            } catch (ignore: InterruptedException) {

            }

            return ads
        }

        override fun deliverResult(data: List<NendAdFullBoard>?) {
            ads = data
            if (isStarted) {
                super.deliverResult(data)
            }
        }

        override fun onStartLoading() {
            ads?.let {
                deliverResult(it)
            } ?: run {
                forceLoad()
            }
        }

        override fun onStopLoading() {
            cancelLoad()
        }

        override fun onReset() {
            super.onReset()
            onStopLoading()
            ads = null
        }
    }

    private inner class ContentPage : Page {

        override val fragment: Fragment
            get() = ContentFragment()
    }

    private inner class AdPage internal constructor(private val ad: NendAdFullBoard) : Page {

        override val fragment: Fragment
            get() = AdFragment().apply { setAd(ad) }
    }

    private inner class Adapter internal constructor(
            fm: FragmentManager, private val pages: List<Page>) : FragmentPagerAdapter(fm) {

        override fun getItemPosition(`object`: Any): Int {
            return if (`object` is AdFragment) {
                PagerAdapter.POSITION_NONE
            } else {
                super.getItemPosition(`object`)
            }
        }

        override fun getItem(position: Int) = pages[position].fragment

        override fun getCount() = pages.size
    }

    companion object {
        private const val AD_COUNT = 2
    }
}