package id.pamoyanan_dev.f_catlist

import id.pamoyanan_dev.l_extras.data.model.Result

interface CatListItemCallback {
    fun onMovieClicked(item: Result)
}