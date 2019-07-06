package id.pamoyanan_dev.l_extras.data.model

data class ApiResponse<T>(val status_code: Int, val message: String, val data: T?)