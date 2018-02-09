package net.nend.sample.kotlin.fullboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast

import net.nend.android.NendAdFullBoard
import net.nend.android.NendAdFullBoardLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_API_KEY
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_LOG_TAG
import net.nend.sample.kotlin.fullboard.FullBoardMenuActivity.Companion.FULLBOARD_SPOT_ID

@Suppress("UNUSED_PARAMETER")
class FullBoardDefaultActivity : AppCompatActivity() {

    private lateinit var loader: NendAdFullBoardLoader
    private var ad: NendAdFullBoard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_board)

        loader = NendAdFullBoardLoader(applicationContext, FULLBOARD_SPOT_ID, FULLBOARD_API_KEY)
    }

    fun onClickLoad(view: View) {
        loader.loadAd(object : NendAdFullBoardLoader.Callback {
            override fun onSuccess(nendAdFullBoard: NendAdFullBoard) {
                Toast.makeText(this@FullBoardDefaultActivity,
                        "Loaded", Toast.LENGTH_SHORT).show()
                ad = nendAdFullBoard
            }

            override fun onFailure(fullBoardAdError: NendAdFullBoardLoader.FullBoardAdError) {
                Log.w(FULLBOARD_LOG_TAG, fullBoardAdError.name)
            }
        })
    }

    fun onClickShow(view: View) {
        ad?.show(this)
    }
}