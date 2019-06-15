package id.ac.unpad.profolio.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.support.v7.app.AlertDialog

class DialogUtil(private val context: Context) {

    private var progressDialog: ProgressDialog = ProgressDialog(context)

    fun openProgressDialog(
            message: String,
            title: String,
            cancelOutsideDialog: Boolean
    ): ProgressDialog {
        return progressDialog.apply {
            setTitle(title)
            setMessage(message)
            setCanceledOnTouchOutside(cancelOutsideDialog)
        }
    }

    fun closeProgressDialog() {
        Handler().postDelayed({
            openProgressDialog("", "", false).dismiss()
        }, 1000)
    }

    fun openAlertDialog(
            message: String?,
            title: String?,
            cancelOutsideDialog: Boolean,
            positiveTitle: String,
            negativeTitle: String,
            alertDialogCallback: AlertCallbackDialog
    ): Dialog {
        return AlertDialog
                .Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancelOutsideDialog)
                .setPositiveButton(positiveTitle) { dialog, which ->
                    alertDialogCallback.onPositiveDialogClicked(
                            dialogInterface = dialog,
                            which = which
                    )
                }
                .setNegativeButton(negativeTitle) { dialog, which ->
                    alertDialogCallback.onNegativeDialogClicked(
                            dialogInterface = dialog,
                            which = which
                    )
                }
                .create()
    }

    interface AlertCallbackDialog {
        fun onPositiveDialogClicked(dialogInterface: DialogInterface?, which: Int)
        fun onNegativeDialogClicked(dialogInterface: DialogInterface?, which: Int)
    }

}
