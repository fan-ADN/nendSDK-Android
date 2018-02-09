package net.nend.sample.kotlin.nativead

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.native_marquee.*
import net.nend.android.NendAdNative
import net.nend.android.NendAdNativeClient
import net.nend.android.NendAdNativeViewBinder
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_API_KEY_SMALL_SQUARE
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_LOG_TAG
import net.nend.sample.kotlin.nativead.NativeSampleActivity.Companion.NATIVE_SPOT_ID_SMALL_SQUARE

/**
 * Sample for like telop
 */
class NativeMarqueeActivity : AppCompatActivity() {

    private lateinit var marqueeView: MarqueeView
    private lateinit var textView: TextView
    private lateinit var adContainer: View

    private var threadStopper = false // for leaving the loop
    private var scrollViewWidth: Int = 0           // MarqueeView width

    /**
     * Get end coordinate
     */
    private var endX: Int = 0            // NendNativeTextView width
    private lateinit var thread: Thread          // runnable thread
    private var currentX: Int = 0                  // current text position
    private var isEndlessLoop: Boolean = false         // endless flag

    /**
     * Runnable variable for marquee
     */
    private val runnable = Runnable {
        // end point
        val endX = endX
        val textMoveSpeed = 10

        if (!threadStopper && !Thread.interrupted()) {

            currentX = marqueeStartX

            var beforeTime = System.currentTimeMillis()
            var afterTime: Long
            val fps = 30
            val frameTime = (1000 / fps).toLong()

            while (true) {
                if (!threadStopper && !Thread.interrupted()) {

                    if (currentX >= endX) {
                        currentX = marqueeStartX
                        if (!isEndlessLoop) {
                            break
                        }
                    }

                    currentX += textMoveSpeed

                    // UI control must on UI thread
                    runOnUiThread { marqueeView.smoothScrollTo(currentX, 0) }

                    // speed control
                    afterTime = System.currentTimeMillis()
                    val pastTime = afterTime - beforeTime
                    val sleepTime = frameTime - pastTime
                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime)
                        } catch (e: InterruptedException) {
                            Log.d(NATIVE_LOG_TAG, "thread interruption")
                            break
                        }

                    }
                    beforeTime = System.currentTimeMillis()
                }
            }
        }
    }

    /**
     * Get start coordinate
     */
    private val marqueeStartX: Int
        get() = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.native_marquee)

        marqueeView = findViewById(R.id.horizontalScrollView) as MarqueeView
        textView = findViewById(R.id.ad_content) as TextView
        adContainer = findViewById(R.id.ad)

        val binder = NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.AD)
                .build()

        // small square size
        NendAdNativeClient(
                applicationContext, NATIVE_SPOT_ID_SMALL_SQUARE, NATIVE_API_KEY_SMALL_SQUARE).apply {
            loadAd(object : NendAdNativeClient.Callback {
                override fun onSuccess(nendAdNative: NendAdNative) {
                    Log.i(NATIVE_LOG_TAG, "広告取得成功")
                    nendAdNative.intoView(adContainer, binder)
                    setPortraitPR()
                    setBlank()
                }

                override fun onFailure(nendError: NendAdNativeClient.NendError) {
                    Log.i(NATIVE_LOG_TAG, "広告取得失敗: ${nendError.message}")
                }
            })
        }

        setEndless(true)
    }

    /**
     * Set blank to title text
     */
    private fun setBlank() {
        // get future title text size
        val nendTextWidth = Layout.getDesiredWidth(textView.text, textView.paint).toInt()
        endX = nendTextWidth + scrollViewWidth * 2
        // first, set text view size
        textView.width = endX
        // second, set padding
        textView.setPadding(scrollViewWidth, 0, scrollViewWidth, 0)
        startMarquee()
    }

    /**
     * Set portrait to PR text
     */
    private fun setPortraitPR() {
        val scale = resources.displayMetrics.density
        val paddingDp = (10 * scale + 0.5f).toInt()
        // set future pr size plus 10dp
        val prWidth = Layout.getDesiredWidth(ad_pr.text, ad_pr.paint).toInt() + paddingDp
        ad_pr.width = prWidth / 2
    }

    private fun setEndless(isEndless: Boolean) {
        isEndlessLoop = isEndless
    }

    /**
     * clear marquee
     */
    fun clearMarquee() {
        currentX = marqueeStartX
        threadStopper = true
    }

    /**
     * start marquee
     */
    fun startMarquee() {
        clearMarquee()
        threadStopper = false
        thread = Thread(runnable).apply {
            name = "marquee"
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        // true to leaving the loop
        threadStopper = true
        if (thread.name == "marquee" && !thread.isInterrupted) {
            thread.interrupt()
        }
        clearMarquee()
    }

    override fun onResume() {
        super.onResume()
        if (threadStopper) {
            threadStopper = false
            startMarquee()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            scrollViewWidth = marqueeView.width
        } else {
            threadStopper = true
            if (!thread.isInterrupted) {
                thread.interrupt()
            }
            clearMarquee()
        }
    }
}