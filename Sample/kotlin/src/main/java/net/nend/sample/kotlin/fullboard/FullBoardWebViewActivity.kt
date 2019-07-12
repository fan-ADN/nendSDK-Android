package net.nend.sample.kotlin.fullboard

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_full_board_web.*
import net.nend.android.NendAdFullBoard
import net.nend.android.NendAdFullBoardLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_API_KEY
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_LOG_TAG
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_SPOT_ID

class FullBoardWebViewActivity : AppCompatActivity(), NendAdFullBoard.FullBoardAdListener {

    private lateinit var loader: NendAdFullBoardLoader
    private var ad: NendAdFullBoard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_board_web)

        webview.loadUrl("https://board.nend.net")
        webview.setCallback(object : ObservableWebView.Callback {
            override fun onScrollEnd() {
                ad?.let {
                    Handler().postDelayed({ it.show(this@FullBoardWebViewActivity) }, 500L)
                }
            }
        })

        loader = NendAdFullBoardLoader(applicationContext, FULLBOARD_SPOT_ID, FULLBOARD_API_KEY)
        loadAd()
    }

    private fun loadAd() {
        loader.loadAd(object : NendAdFullBoardLoader.Callback {
            override fun onSuccess(nendAdFullBoard: NendAdFullBoard) {
                ad = nendAdFullBoard
                ad?.setAdListener(this@FullBoardWebViewActivity)
            }

            override fun onFailure(fullBoardAdError: NendAdFullBoardLoader.FullBoardAdError) {
                Log.w(FULLBOARD_LOG_TAG, fullBoardAdError.name)
            }
        })
    }

    override fun onDismissAd(nendAdFullBoard: NendAdFullBoard) {
        loadAd()
    }

    override fun onShowAd(nendAdFullBoard: NendAdFullBoard) {

    }

    override fun onClickAd(nendAdFullBoard: NendAdFullBoard) {

    }
}