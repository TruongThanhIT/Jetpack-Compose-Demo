package thanh.truong.jetpackcomposedemo.ui.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import thanh.truong.jetpackcomposedemo.ui.model.User

interface DummyService {
    @GET("/data/v1/user")
    suspend fun getUsers(
        @Header("app-id") appId: String = "612a3cb17616e958ff03af6f",
        @Query("limit") limit: Int = 10
    ): Response<BaseResponse<List<User>>>
    @GET("/data/v1/user/{user-id}")
    suspend fun getUserById(
        @Header("app-id") appId: String = "612a3cb17616e958ff03af6f",
        @Path("user-id") userId: String
    ): Response<User>
}