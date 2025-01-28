package com.example.projectakhirpam_a13.repository

import com.example.projectakhirpam_a13.model.AllTransaksiResponse
import com.example.projectakhirpam_a13.model.Transaksi
import com.example.projectakhirpam_a13.service.TransaksiService
import java.io.IOException

interface TransaksiRepository {
    suspend fun getTransaksi(): AllTransaksiResponse

    suspend fun insertTransaksi(transaksi: Transaksi)

    suspend fun deleteTransaksi(idTransaksi: Int)

    suspend fun getTransaksiById(idTransaksi: Int): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiApiService: TransaksiService
) : TransaksiRepository {
    override suspend fun getTransaksi(): AllTransaksiResponse =
        transaksiApiService.getAllTransaksi()

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }


    override suspend fun deleteTransaksi(idTransaksi: Int) {
        try {
            val response = transaksiApiService.deleteTransaksi(idTransaksi)
            if (!response.isSuccessful) {
                throw IOException(
                    "Gagal Hapus Transaksi. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTransaksiById(idTransaksi: Int): Transaksi {
        return transaksiApiService.getTransaksiById(idTransaksi).data
    }
}