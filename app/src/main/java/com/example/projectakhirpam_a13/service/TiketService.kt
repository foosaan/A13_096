package com.example.projectakhirpam_a13.service


import com.example.projectakhirpam_a13.model.AllTiketResponse
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.model.TiketDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("tiket")
    suspend fun getAllTiket(): AllTiketResponse

    @GET("tiket/{id_tiket}")
    suspend fun getTiketById(@Path("id_tiket") idTiket: Int): TiketDetailResponse

    @POST("tiket/store")
    suspend fun insertTiket(@Body tiket: Tiket): Response<Void>

    @PUT("tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket") idTiket: Int, @Body tiket: Tiket): Response<Void>

    @DELETE("tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket") idTiket: Int): Response<Void>

    @GET("tiket/{id_event}/kapasitas")
    suspend fun getKapasitasTiket(@Path("id_event") idEvent: Int): Response<Int>  // Mendapatkan kapasitas tiket berdasarkan event
}