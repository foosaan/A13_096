package com.example.projectakhirpam_a13.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    onEventClick: () -> Unit,
    onTiketClick: () -> Unit,
    onPenggunaClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2196F3), // Warna biru terang
                shape = RoundedCornerShape(24.dp)
            )
            .padding(8.dp)
    ) {
        // Tombol Event
        IconButton(
            onClick = onEventClick,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Event",
                color = Color.White, // Warna teks putih
                style = MaterialTheme.typography.bodyMedium, // Menyesuaikan ukuran teks
            )
        }

        // Tombol Pengguna
        IconButton(
            onClick = onPenggunaClick,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Pengguna",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Tombol Tiket
        IconButton(
            onClick = onTiketClick,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Tiket",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Tombol Transaksi
        IconButton(
            onClick = onTransaksiClick,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Transaksi",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}



