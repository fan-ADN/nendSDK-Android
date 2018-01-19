package net.nend.sample.kotlin.icon

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.icon_dialog.*
import net.nend.android.NendAdIconLoader
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_API_KEY
import net.nend.sample.kotlin.icon.IconSampleActivity.Companion.ICON_SPOT_ID

class IconDialogActivity : AppCompatActivity() {

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

        private lateinit var iconLoader: NendAdIconLoader

        init {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.icon_dialog)

            iconLoader = NendAdIconLoader(applicationContext, ICON_SPOT_ID, ICON_API_KEY).apply {
                addIconView(icon1)
                addIconView(icon2)
                addIconView(icon3)
                addIconView(icon4)
            }
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            iconLoader.loadAd()
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            iconLoader.pause()
        }

        fun destroy() {
            dismiss()
        }
    }
}