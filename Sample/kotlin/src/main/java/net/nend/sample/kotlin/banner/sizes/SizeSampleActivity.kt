package net.nend.sample.kotlin.banner.sizes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import net.nend.sample.kotlin.R
import java.util.*

class SizeSampleActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateListFragment()
    }

    private fun instantiateListFragment() {
        val id = Random().nextInt(0xFFFF)
        val container = FrameLayout(this)
        container.id = id
        setContentView(container, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        supportFragmentManager
                .beginTransaction()
                .add(id, SizeSampleListFragment().apply {
                    arguments = Bundle().apply {
                        putInt("resId", R.layout.size_sample)
                    }
                })
                .commit()
    }

    class SizeSampleListFragment : ListFragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            setHasOptionsMenu(true)
            return inflater.inflate(requireArguments().getInt("resId"), container, false)
        }

        override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
            Intent(context, SizeActivity::class.java).run {
                putExtra("size_type", position)
                startActivity(this)
            }
        }
    }
}