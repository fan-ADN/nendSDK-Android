package net.nend.sample.kotlin.fullboard

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class ObservableWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {

    private var callback: Callback? = null

    internal fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val height = Math.floor(
                (contentHeight * resources.displayMetrics.density).toDouble()).toInt()
        val webViewHeight = getHeight()
        callback?.let {
            if (t > oldt && scrollY + webViewHeight >= height - 5) {
                it.onScrollEnd()
            }
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

    internal interface Callback {
        fun onScrollEnd()
    }
}