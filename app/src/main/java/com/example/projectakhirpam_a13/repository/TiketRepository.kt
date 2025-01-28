package com.example.projectakhirpam_a13.repository

import com.example.projectakhirpam_a13.model.AllTiketResponse
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.service.TiketService
import java.io.IOException

interface TiketRepository {
    suspend fun getTiket(): AllTiketResponse

    suspend fun insertTiket(tiket: Tiket)

    suspend fun updateTiket(idTiket: Int, tiket: Tiket)

    suspend fun deleteTiket(idTiket: Int)

    suspend fun getTiketById(idTiket: Int): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun getTiket(): AllTiketResponse =
        tiketApiService.getAllTiket()

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(idTiket: Int, tiket: Tiket) {
        tiketApiService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: Int) {
        try {
            val response = tiketApiService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw IOException(
                    "Gagal Hapus Tiket. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTiketById(idTiket: Int): Tiket {
        return tiketApiService.getTiketById(idTiket).data
    }
}