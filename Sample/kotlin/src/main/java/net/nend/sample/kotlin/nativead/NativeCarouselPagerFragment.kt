package net.nend.sample.kotlin.nativead

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import net.nend.sample.kotlin.R

class NativeCarouselPagerFragment : Fragment() {

    private var callback: OnAdListener? = null
    var layoutId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = activity as OnAdListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId,
                container, false).apply {
            val adLayout = findViewById(R.id.ad) as RelativeLayout
            callback?.onAdRequest(adLayout, arguments!!.getInt("position"))
        }
    }

    interface OnAdListener {
        fun onAdRequest(view: View, position: Int)
    }

    companion object {
        internal fun newInstance(position: Int, layoutId: Int): NativeCarouselPagerFragment {
            return NativeCarouselPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                }
                this.layoutId = layoutId
            }
        }
    }
}