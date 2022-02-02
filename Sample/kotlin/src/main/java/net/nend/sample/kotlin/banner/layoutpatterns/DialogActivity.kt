package net.nend.sample.kotlin.banner.layoutpatterns

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import net.nend.sample.kotlin.R
import net.nend.sample.kotlin.databinding.DialogBinding

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

        private lateinit var binding: DialogBinding

        init {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DialogBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        override fun onAttachedToWindow() {
            super.onAttachedToWindow()
            binding.nend.loadAd()
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            binding.nend.pause()
        }

        fun destroy() {
            dismiss()
        }
    }
}