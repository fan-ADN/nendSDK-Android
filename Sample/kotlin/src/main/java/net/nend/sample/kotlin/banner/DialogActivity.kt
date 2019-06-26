package net.nend.sample.kotlin.banner

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.dialog.*
import net.nend.sample.kotlin.R

class DialogActivity : AppCompatActivity() {

    private lateinit var dialog: NendDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        dialog = NendDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.destroy()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
        dialog.show()
    }

    internal inner class NendDialog(context: Context) : Dialog(context) {
        init {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog)
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            nend.loadAd()
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            nend.pause()
        }

        fun destroy() {
            dismiss()
        }
    }
}