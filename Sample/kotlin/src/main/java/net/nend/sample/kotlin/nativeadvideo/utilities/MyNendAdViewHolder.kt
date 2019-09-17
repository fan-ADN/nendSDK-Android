package net.nend.sample.kotlin.nativeadvideo.utilities

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import net.nend.android.NendAdNativeMediaView
import net.nend.android.NendAdNativeMediaStateListener
import net.nend.android.NendAdNativeVideo
import net.nend.android.NendAdNativeViewBinder

class MyNendAdViewHolder : RecyclerView.ViewHolder {

    lateinit var myItemView: View
    //Common
    var logoImageView: ImageView? = null
    var titleTextView: TextView? = null
    var contentTextView: TextView? = null
    var prTextView: TextView? = null
    var actionTextView: TextView? = null

    //Image
    lateinit var normalBinder: NendAdNativeViewBinder

    //Video
    private var videoBinder: MyNendAdViewBinder? = null
    var adNativeMediaView: NendAdNativeMediaView? = null
    var advertiserNameTextView: TextView? = null
    var ratingStarsLinearLayout: LinearLayout? = null
    var ratingCountTextView: TextView? = null

    constructor(itemView: View, viewBinder: MyNendAdViewBinder?) : super(itemView) {
        if (viewBinder == null) {
            return
        }

        this.myItemView = itemView
        this.videoBinder = viewBinder

        try {
            adNativeMediaView = itemView.findViewById(viewBinder.mediaViewId)
            logoImageView = itemView.findViewById(viewBinder.logoImageId)
            titleTextView = itemView.findViewById(viewBinder.titleId)
            contentTextView = itemView.findViewById(viewBinder.contentId)
            advertiserNameTextView = itemView.findViewById(viewBinder.advertiserId)
            ratingStarsLinearLayout = itemView.findViewById(viewBinder.ratingId)
            ratingCountTextView = itemView.findViewById(viewBinder.ratingCountId)
            actionTextView = itemView.findViewById(viewBinder.actionId)
        } catch (exception: ClassCastException) {
            logoImageView = null
            titleTextView = null
            contentTextView = null
            prTextView = null
            ratingStarsLinearLayout = null
            ratingCountTextView = null
            actionTextView = null
        }

    }

    constructor(itemView: View, viewBinder: NendAdNativeViewBinder?) : super(itemView) {
        if (viewBinder == null) {
            return
        }

        this.myItemView = itemView
        this.normalBinder = viewBinder

        try {
            logoImageView = itemView.findViewById(viewBinder.logoImageId)
            titleTextView = itemView.findViewById(viewBinder.titleId)
            contentTextView = itemView.findViewById(viewBinder.contentId)
            prTextView = itemView.findViewById(viewBinder.prId)
            actionTextView = itemView.findViewById(viewBinder.actionId)
        } catch (exception: ClassCastException) {
            logoImageView = null
            titleTextView = null
            contentTextView = null
            prTextView = null
            actionTextView = null
        }

    }

    fun renderView(ad: NendAdNativeVideo, listener: NendAdNativeMediaStateListener?) {
        videoBinder?.renderView(this, ad, listener)
    }
}
