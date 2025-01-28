package com.example.projectakhirpam_a13.service

import com.example.projectakhirpam_a13.model.AllPesertaResponse
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.model.PesertaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PesertaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("peserta")
    suspend fun getAllPeserta(): AllPesertaResponse

    @GET("peserta/{id_peserta}")
    suspend fun getPesertaById(@Path("id_peserta") idPeserta: Int): PesertaDetailResponse

    @POST("peserta/store")
    suspend fun insertPeserta(@Body peserta: Peserta): Response<Void>

    @PUT("peserta/{id_peserta}")
    suspend fun updatePeserta(@Path("id_peserta") idPeserta: Int, @Body peserta: Peserta): Response<Void>

    @DELETE("peserta/{id_peserta}")
    suspend fun deletePeserta(@Path("id_peserta") idPeserta: Int): Response<Void>
}