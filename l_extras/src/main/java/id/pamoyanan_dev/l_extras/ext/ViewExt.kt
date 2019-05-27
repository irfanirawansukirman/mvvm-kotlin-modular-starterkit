package id.pamoyanan_dev.l_extras.ext

import android.support.design.widget.Snackbar
import android.view.View

fun View.visible(

) {
    if (this.visibility == View.GONE) this.visibility = View.VISIBLE
}

fun View.gone(

) {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun View.invisible(

) {
    if (this.visibility == View.VISIBLE) this.visibility = View.INVISIBLE
}

fun View.showSnackbarDefault(
    message: String,
    duration: Int = 3000
) {
    Snackbar.make(this, message, duration).show()
}