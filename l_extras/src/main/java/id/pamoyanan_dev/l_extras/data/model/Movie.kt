package id.pamoyanan_dev.l_extras.data.model

import kotlinx.serialization.Serializable

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
@Serializable
data class Movie(
    var vote_count: Int? = null,
    var id: Int? = null,
    var isVideo: Boolean? = null,
    var vote_average: Double? = null,
    var title: String? = null,
    var popularity: Double? = null,
    var poster_path: String? = null,
    var original_language: String? = null,
    var original_title: String? = null,
    var backdrop_path: String? = null,
    var isAdult: Boolean? = null,
    var overview: String? = null,
    var release_date: String? = null
)

@Serializable
data class TmdbMovieResponse(
    val results: List<Movie>
)