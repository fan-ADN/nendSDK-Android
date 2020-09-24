package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.attach_dettach.*
import net.nend.sample.kotlin.R

/**
 * NendAdViewを削除し、再表示するサンプル
 */
class AttachAndDetachActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private var isAttached = true

    /**
     * NendAdViewの削除、再表示処理
     */
    private val runnable = Runnable {
        isAttached = when (isAttached) {
            true -> {
                root.removeView(nend)
                false
            }
            else -> {
                root.addView(nend)
                true
            }
        }
        doPost()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attach_dettach)

        handler = Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        // 繰り返し処理を開始
        doPost()
    }

    override fun onPause() {
        super.onPause()
        // 繰り返し処理を停止
        handler.removeCallbacks(runnable)
    }

    private fun doPost() {
        // 5秒ごとに削除と再表示を繰り返す
        handler.postDelayed(runnable, 5000)
    }
}