package thanh.truong.jetpackcomposedemo.ui.network

import androidx.annotation.Keep

@Keep
data class BaseResponse<T>(
    val data: T? = null,
    val total: Int? = null,
    val page: Int? = null,
    val limit: Int? = null
)