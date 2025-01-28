package com.example.projectakhirpam_a13.ui.viewModel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.loket.ui.viewModel.peserta.PesertaInsertViewModel
import com.example.loket.ui.viewModel.peserta.PesertaUpdateViewModel
import com.example.projectakhirpam_a13.LoketApplication
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.model.Transaksi
import com.example.projectakhirpam_a13.ui.viewModel.event.EventDetailViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventHomeViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventInsertViewModel
import com.example.projectakhirpam_a13.ui.viewModel.event.EventUpdateViewModel
import com.example.projectakhirpam_a13.ui.viewModel.peserta.PesertaDetailViewModel
import com.example.projectakhirpam_a13.ui.viewModel.peserta.PesertaHomeViewModel

//import com.example.loket.ui.viewModel.tiket.TiketInsertViewModel
//import com.example.loket.ui.viewModel.tiket.TiketUpdateViewModel
//import com.example.loket.ui.viewModel.transaksi.TransaksiHomeViewModel
//import com.example.loket.ui.viewModel.transaksi.TransaksiInsertViewModel

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        // Event
        initializer { EventHomeViewModel(aplikasiLoket().container.eventRepository) }
        initializer { EventInsertViewModel(aplikasiLoket().container.eventRepository) }
        initializer { EventUpdateViewModel(createSavedStateHandle(), aplikasiLoket().container.eventRepository) }
        initializer { EventDetailViewModel(createSavedStateHandle(), aplikasiLoket().container.eventRepository) }

         Peserta
        initializer { PesertaHomeViewModel(aplikasiLoket().container.pesertaRepository) }
        initializer { PesertaInsertViewModel(aplikasiLoket().container.pesertaRepository) }
        initializer { PesertaUpdateViewModel(createSavedStateHandle(), aplikasiLoket().container.pesertaRepository) }
        initializer { PesertaDetailViewModel(createSavedStateHandle(), aplikasiLoket().container.pesertaRepository) }
//
//         Tiket
//        initializer { TiketHomeViewModel(aplikasiLoket().container.tiketRepository) }
//        initializer { TiketInsertViewModel(aplikasiLoket().container.tiketRepository) }
//        initializer { TiketUpdateViewModel(createSavedStateHandle(), aplikasiLoket().container.tiketRepository) }
//
//         Transaksi
//        initializer { TransaksiHomeViewModel(aplikasiLoket().container.transaksiRepository) }
//        initializer { TransaksiInsertViewModel(aplikasiLoket().container.transaksiRepository) }
    }
}

fun CreationExtras.aplikasiLoket(): LoketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LoketApplication)
