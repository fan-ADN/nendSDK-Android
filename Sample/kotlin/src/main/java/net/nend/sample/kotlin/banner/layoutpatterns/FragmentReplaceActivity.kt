package net.nend.sample.kotlin.banner.layoutpatterns

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment.*
import net.nend.sample.kotlin.R

/**
 * Fragment切り替えサンプル
 */
class FragmentReplaceActivity : AppCompatActivity(), OnClickListener {

    private var count = 0

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment)

        button.setOnClickListener(this)

        supportFragmentManager.beginTransaction().run {
            add(R.id.layout, FirstFragment())
            commit()
        }
    }

    @SuppressLint("CommitTransaction")
    override fun onClick(v: View) {
        var fragment: Fragment? = null
        when (count % 2) {
            0 -> fragment = SecondFragment()
            1 -> fragment = FirstFragment()
        }
        count++

        supportFragmentManager.beginTransaction().run {
            replace(R.id.layout, fragment!!)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }

    class FirstFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?, savedInstanceState: Bundle?): View? =
                View.inflate(context, R.layout.replace_fragment1, null)
    }

    class SecondFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?, savedInstanceState: Bundle?): View? =
                View.inflate(context, R.layout.replace_fragment2, null)
    }
}
