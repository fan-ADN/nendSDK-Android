package net.nend.sample.kotlin.banner

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.nend.sample.kotlin.R

class SecondFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?) =
            View.inflate(context, R.layout.replace_fragment2, null)
}