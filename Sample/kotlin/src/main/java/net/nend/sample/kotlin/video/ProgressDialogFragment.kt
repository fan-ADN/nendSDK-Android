package net.nend.sample.kotlin.video

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import android.widget.TextView
import net.nend.sample.kotlin.R

class ProgressDialogFragment : androidx.fragment.app.DialogFragment() {

    private lateinit var messageTextView: TextView
    private var isShowing: Boolean = false
    var dialogMessage: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_dialog, null, false))
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog.setCancelable(false)
        messageTextView = dialog.findViewById(R.id.progress_message) as TextView
        messageTextView.text = dialogMessage
    }

    override fun show(manager: androidx.fragment.app.FragmentManager, tag: String) {
        super.show(manager, tag)
        isShowing = true
    }

    fun cancel() {
        dismiss()
        isShowing = false
    }

    fun isShowing(): Boolean = isShowing

    companion object {
        fun newInstance() = ProgressDialogFragment()
    }
}