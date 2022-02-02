package net.nend.sample.kotlin.fullboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.nend.android.NendAdFullBoard
import net.nend.android.NendAdFullBoardLoader
import net.nend.android.NendAdFullBoardView
import net.nend.sample.kotlin.databinding.ActivityFullBoardTabLayoutBinding
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_API_KEY
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_SPOT_ID

class FullBoardTabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullBoardTabLayoutBinding
    private var fullBoardAd: NendAdFullBoard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullBoardTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NendAdFullBoardLoader(applicationContext, FULLBOARD_SPOT_ID, FULLBOARD_API_KEY).apply {
            loadAd(object : NendAdFullBoardLoader.Callback {
                override fun onSuccess(nendAdFullBoard: NendAdFullBoard) {
                    populateTabs(nendAdFullBoard)
                }

                override fun onFailure(fullBoardAdError: NendAdFullBoardLoader.FullBoardAdError) {
                    finish()
                }
            })
        }
    }

    private fun populateTabs(ad: NendAdFullBoard) {
        fullBoardAd = ad

        binding.tab.addTab(binding.tab.newTab())
        binding.tab.addTab(binding.tab.newTab())
        binding.tab.addTab(binding.tab.newTab())

        binding.pager.adapter = Adapter(supportFragmentManager)
        binding.tab.setupWithViewPager(binding.pager)
    }

    class ListFragment : androidx.fragment.app.ListFragment() {

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            listAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, ITEMS)
        }

        companion object {
            private val ITEMS = arrayOfNulls<String>(50)

            init {
                for (i in ITEMS.indices) {
                    ITEMS[i] = "Item${i + 1}"
                }
            }
        }
    }

    class AdFragment : Fragment() {

        private var fullBoard: NendAdFullBoard? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return if (fullBoard != null) {
                NendAdFullBoardView.Builder.with(activity, fullBoard).build()
            } else {
                null
            }
        }

        internal fun setAd(ad: NendAdFullBoard?) {
            fullBoard = ad
        }
    }

    private inner class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount() = 3

        override fun getItem(position: Int): Fragment {
            return if (position <= 1) {
                ListFragment()
            } else {
                AdFragment().apply {
                    setAd(fullBoardAd)
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            when (position) {
                0 -> return "First"
                1 -> return "Second"
                2 -> return "PR"
            }
            return super.getPageTitle(position)!!
        }
    }
}