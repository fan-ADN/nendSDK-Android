package net.nend.sample.kotlin.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.nend.sample.kotlin.R

class SecondFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?) =
            View.inflate(context, R.layout.replace_fragment2, null)
}