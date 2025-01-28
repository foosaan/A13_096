package com.example.projectakhirpam_a13.repository

import com.example.projectakhirpam_a13.model.AllPesertaResponse
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.service.PesertaService
import java.io.IOException

interface PesertaRepository {
    suspend fun getPeserta(): AllPesertaResponse
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(idPeserta: Int, peserta: Peserta)
    suspend fun deletePeserta(idPeserta: Int)
    suspend fun getPesertaById(idPeserta: Int): Peserta
}
class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
) : PesertaRepository {
    override suspend fun getPeserta(): AllPesertaResponse =
        pesertaApiService.getAllPeserta()

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(idPeserta: Int, peserta: Peserta) {
        pesertaApiService.updatePeserta(idPeserta, peserta)
    }
    override suspend fun deletePeserta(idPeserta: Int) {
        try {
            val response = pesertaApiService.deletePeserta(idPeserta)
            if (!response.isSuccessful) {
                throw IOException(
                    "Gagal Hapus Peserta. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPesertaById(idPeserta: Int): Peserta {
        return pesertaApiService.getPesertaById(idPeserta).data
    }
}