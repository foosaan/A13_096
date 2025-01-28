package com.example.projectakhirpam_a13.model

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable


@Entity(
    tableName = "Tiket",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id_event"],
            childColumns = ["id_event"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Peserta::class,
            parentColumns = ["id_peserta"],
            childColumns = ["id_peserta"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

@Serializable
data class Tiket(
    val id_tiket: Int,
    val id_event: Int,
    val kapasitas_tiket: String,
    val harga_tiket: String,
    val id_peserta: Int,
)

@Serializable
data class AllTiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket> // Daftar tiket
)

@Serializable
data class TiketDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tiket // Detail dari tiket
)
