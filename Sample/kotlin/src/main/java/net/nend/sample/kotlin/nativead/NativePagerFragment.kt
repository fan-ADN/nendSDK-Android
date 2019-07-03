package net.nend.sample.kotlin.nativead

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import net.nend.sample.kotlin.R

class NativePagerFragment : androidx.fragment.app.Fragment() {

    internal lateinit var callback: OnAdListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = activity
        try {
            callback = activity as OnAdListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context, R.layout.native_fragment, null).apply {
            val adLayout = findViewById(R.id.ad) as RelativeLayout
            callback.onAdRequest(adLayout, arguments!!.getInt("position"))
        }
    }

    interface OnAdListener {
        fun onAdRequest(view: View, position: Int)
    }
}