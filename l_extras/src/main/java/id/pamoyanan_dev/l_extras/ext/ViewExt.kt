package id.pamoyanan_dev.l_extras.ext

import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

fun RecyclerView.verticalListStyle() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.horizontalListStyle() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}