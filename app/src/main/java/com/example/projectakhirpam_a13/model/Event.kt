package com.example.projectakhirpam_a13.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id_event: Int,
    val nama_event: String,
    val deskripsi_event: String,
    val tanggal_event: String,
    val lokasi_event: String
)

@Serializable
data class AllEventResponse(
    val status: Boolean,
    val message: String,
    val data: List<Event> // Daftar event
)

@Serializable
data class EventDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Event // Detail dari event
)