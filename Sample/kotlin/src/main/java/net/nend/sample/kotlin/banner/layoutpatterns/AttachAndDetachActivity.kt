package net.nend.sample.kotlin.banner.layoutpatterns

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import net.nend.android.NendAdView
import net.nend.sample.kotlin.databinding.AttachDettachBinding

/**
 * NendAdViewを削除し、再表示するサンプル
 */
class AttachAndDetachActivity : AppCompatActivity() {

    private lateinit var binding: AttachDettachBinding
    private lateinit var handler: Handler
    private var isAttached = true

    /**
     * NendAdViewの削除、再表示処理
     */
    private fun handleNendAd(layout: ViewGroup, nendAd: NendAdView): Runnable = Runnable {
        isAttached = when (isAttached) {
            true -> {
                layout.removeView(nendAd)
                false
            }
            else -> {
                layout.addView(nendAd)
                true
            }
        }
        doPost()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AttachDettachBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        handler.removeCallbacks(handleNendAd(binding.root, binding.nend))
    }

    private fun doPost() {
        // 5秒ごとに削除と再表示を繰り返す
        handler.postDelayed(handleNendAd(binding.root, binding.nend), 5000)
    }
}