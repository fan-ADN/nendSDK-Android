package net.nend.sample.kotlin

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import kotlin.random.Random

open class SimpleListActivity: AppCompatActivity() {

    lateinit var listFragment: ListFragment

    protected fun instantiateMenuListFragment(resId: Int, menus: List<String>){
        val id = Random.nextInt(0xFFFF)
        val container = FrameLayout(this)

        container.id = id
        setContentView(container, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ))

        val fragment = SimpleMenuListFragment().apply {
            arguments = Bundle().apply {
                putInt("resId", resId)
                putStringArrayList("menus", ArrayList(menus))
            }
        }

        supportFragmentManager.beginTransaction()
                .add(id, fragment)
                .commit()
    }

    protected fun instantiateListAdapter(adapter: ArrayAdapter<*>?) {
        listFragment = ListFragment()
        listFragment.listAdapter = adapter
        supportFragmentManager
                .beginTransaction()
                .add(R.id.content, listFragment, "list_fragment")
                .commit()
    }

    private class SimpleMenuListFragment : ListFragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            setHasOptionsMenu(true)
            return inflater.inflate(requireArguments().getInt("resId"), container, false)
        }

        override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
            try {
                val activity = Class.forName(requireArguments().getStringArrayList("menus")!![position])
                val intent = Intent(getActivity(), activity)
                startActivity(intent)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}