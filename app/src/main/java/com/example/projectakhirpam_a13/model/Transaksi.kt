package com.example.projectakhirpam_a13.model

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "Transaksi",
    foreignKeys = [
        ForeignKey(
            entity = Tiket::class,
            parentColumns = ["id_tiket"],
            childColumns = ["id_event"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tiket::class,
            parentColumns = ["Jumlah_tiket"],
            childColumns = ["Jumlah_tiket"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)


@Serializable
data class Transaksi(
    val id_transaksi: Int,
    val id_tiket: Int,
    val jumlah_tiket: Int,
    val jumlah_pembayaran: Double,
    val tanggal_transaksi: String
)

@Serializable
data class AllTransaksiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Transaksi> // Daftar transaksi
)

@Serializable
data class TransaksiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Transaksi // Detail dari transaksi
)