package com.example.projectakhirpam_a13.depedencies

import com.example.projectakhirpam_a13.repository.EventRepository
import com.example.projectakhirpam_a13.repository.NetworkEventRepository
import com.example.projectakhirpam_a13.repository.NetworkPesertaRepository
import com.example.projectakhirpam_a13.repository.NetworkTiketRepository
import com.example.projectakhirpam_a13.repository.NetworkTransaksiRepository
import com.example.projectakhirpam_a13.repository.PesertaRepository
import com.example.projectakhirpam_a13.repository.TiketRepository
import com.example.projectakhirpam_a13.repository.TransaksiRepository
import com.example.projectakhirpam_a13.service.EventService
import com.example.projectakhirpam_a13.service.PesertaService
import com.example.projectakhirpam_a13.service.TiketService
import com.example.projectakhirpam_a13.service.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}

class LoketContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Event service and repository
    private val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }
    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    // Peserta service and repository
    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }
    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    // Tiket service and repository
    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }
    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    // Transaksi service and repository
    private val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }
    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }
}