package net.nend.sample.kotlin.nativeadvideo.utilities

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import net.nend.android.NendAdNativeMediaViewListener
import net.nend.android.NendAdNativeVideo
import net.nend.sample.kotlin.R
import java.text.NumberFormat
import java.util.*

class MyNendAdViewBinder internal constructor(builder: Builder) {

    val mediaViewId = builder.mediaViewId
    val titleId = builder.titleId
    val contentId = builder.contentId
    val actionId = builder.actionId
    val logoImageId = builder.logoImageId
    val advertiserId = builder.advertiserId
    val ratingId = builder.ratingId
    val ratingCountId = builder.ratingCountId

    fun renderView(viewHolder: MyNendAdViewHolder, ad: NendAdNativeVideo, listener: NendAdNativeMediaViewListener?) {
        val list = ArrayList<View>()

        viewHolder.titleTextView?.let {
            renderTextView(it, ad.titleText)
            list.add(it)
        }
        viewHolder.contentTextView?.let {
            renderTextView(it, ad.descriptionText)
            list.add(it)
        }
        viewHolder.actionTextView?.let {
            renderTextView(it, ad.callToActionText)
            list.add(it)
        }
        viewHolder.advertiserNameTextView?.let {
            renderTextView(it, ad.advertiserName)
            list.add(it)
        }
        viewHolder.logoImageView?.let {
            renderImageView(it, ad.logoImageBitmap)
            list.add(it)
        }

        renderRatingInfo(viewHolder.ratingStarsLinearLayout, viewHolder.ratingCountTextView, ad)

        ad.registerInteractionViews(list)
        viewHolder.adNativeMediaView?.apply {
            visibility = View.VISIBLE
            listener?.let {
                setMediaViewListener(it)
            }
            setMedia(ad)
        }
    }

    private fun renderTextView(textView: TextView, text: String) {
        if (textView.visibility == View.VISIBLE && !TextUtils.isEmpty(text)) {
            textView.text = text
        }
    }

    private fun renderImageView(imageView: ImageView, bitmap: Bitmap?) {
        if (imageView.visibility == View.VISIBLE && bitmap != null) {
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun renderRatingInfo(parent: LinearLayout?, textView: TextView?, ad: NendAdNativeVideo) {
        if (parent == null || textView == null) {
            return
        }
        if (ad.userRating < 0) {
            parent.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            return
        }
        val ratingStarIds = intArrayOf(R.id.native_video_ad_star_001, R.id.native_video_ad_star_002, R.id.native_video_ad_star_003, R.id.native_video_ad_star_004, R.id.native_video_ad_star_005)
        var ratingIndex = 0.0f
        for (viewid in ratingStarIds) {
            val drawableId = getStarDrawableId(ratingIndex, ad.userRating)
            val star = parent.findViewById<ImageView>(viewid)
            star.setImageResource(drawableId)
            ratingIndex += 1.0f
        }

        textView.text = generateRatingCountText(ad.userRatingCount.toLong())
    }

    private fun getStarDrawableId(ratingIndex: Float, userRating: Float): Int {
        return if (ratingIndex >= userRating) {
            R.drawable.baseline_star_border_black_24
        } else if (userRating - ratingIndex <= 0.5f) {
            R.drawable.baseline_star_half_black_24
        } else {
            R.drawable.baseline_star_black_24
        }
    }

    private fun generateRatingCountText(userRatingCount: Long): String {
        return "(" + NumberFormat.getNumberInstance().format(userRatingCount) + ")"
    }

    class Builder {

        var mediaViewId: Int = 0
        var logoImageId: Int = 0
        var titleId: Int = 0
        var contentId: Int = 0
        var advertiserId: Int = 0
        var ratingId: Int = 0
        var ratingCountId: Int = 0
        var actionId: Int = 0

        fun mediaViewId(mediaViewId: Int): Builder {
            this.mediaViewId = mediaViewId
            return this
        }

        fun logoImageId(logoImageId: Int): Builder {
            this.logoImageId = logoImageId
            return this
        }

        fun advertiserId(advertiserId: Int): Builder {
            this.advertiserId = advertiserId
            return this
        }

        fun titleId(titleId: Int): Builder {
            this.titleId = titleId
            return this
        }

        fun contentId(contentId: Int): Builder {
            this.contentId = contentId
            return this
        }

        fun ratingId(ratingId: Int): Builder {
            this.ratingId = ratingId
            return this
        }

        fun ratingCountId(ratingCountId: Int): Builder {
            this.ratingCountId = ratingCountId
            return this
        }

        fun actionId(actionId: Int): Builder {
            this.actionId = actionId
            return this
        }

        fun build(): MyNendAdViewBinder {
            return MyNendAdViewBinder(this)
        }
    }

}
