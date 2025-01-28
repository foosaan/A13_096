package com.example.projectakhirpam_a13.service

import com.example.projectakhirpam_a13.model.AllTransaksiResponse
import com.example.projectakhirpam_a13.model.Transaksi
import com.example.projectakhirpam_a13.model.TransaksiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface TransaksiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("transaksi")
    suspend fun getAllTransaksi(): AllTransaksiResponse

    @GET("transaksi/{id_transaksi}")
    suspend fun getTransaksiById(@Path("id_transaksi") idTransaksi: Int): TransaksiDetailResponse

    @POST("transaksi/store")
    suspend fun insertTransaksi(@Body transaksi: Transaksi): Response<Void>

    @DELETE("transaksi/{id_transaksi}")
    suspend fun deleteTransaksi(@Path("id_transaksi") idTransaksi: Int): Response<Void>  // Menghapus transaksi berdasarkan ID

    @GET("transaksi/{id_tiket}/totalharga")
    suspend fun calculateTotalHarga(@Path("id_tiket") idTiket: Int): Response<Double>  // Hitung total harga tiket berdasarkan id_tiket
}