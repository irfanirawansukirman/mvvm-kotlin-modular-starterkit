package id.pamoyanan_dev.l_extras.base

import kotlinx.serialization.Serializable


/**
 * Created by irfanirawansukirman on 26/01/18.
 */
@Serializable
data class BaseApiResponse<T>(
         val code: Int,
         val message: String,
         val data: T? = null,

        // Remove code below if project is running
        var page: Int,
        var total_results: Int,
        var total_pages: Int,
        var results: T? = null
)