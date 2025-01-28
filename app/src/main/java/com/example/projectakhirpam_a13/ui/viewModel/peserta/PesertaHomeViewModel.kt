package com.example.projectakhirpam_a13.ui.viewModel.peserta

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirpam_a13.model.Peserta
import com.example.projectakhirpam_a13.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeUiStatePsrt {
    data class Success(val peserta: List<Peserta>) : HomeUiStatePsrt()
    object Error : HomeUiStatePsrt()
    object Loading : HomeUiStatePsrt()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PesertaHomeViewModel(private val pesertaRepo: PesertaRepository) : ViewModel() {
    var pesertaUIState: HomeUiStatePsrt by mutableStateOf(HomeUiStatePsrt.Loading)
        private set

    init {
        Log.d("HomeViewModelPeserta", "Initializing HomeViewModelPeserta...")
        getPeserta()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPeserta() {
        viewModelScope.launch {
            Log.d("PesertaHomeViewModel", "Fetching list of patients...")
            pesertaUIState = HomeUiStatePsrt.Loading
            pesertaUIState = try {
                val data = pesertaRepo.getPeserta().data
                Log.d("PesertaHomeViewModel", "Successfully fetched patients: ${data.size} items.")
                HomeUiStatePsrt.Success(data)
            } catch (e: IOException) {
                Log.e("PesertaHomeViewModel", "IO Exception while fetching patients: ${e.message}")
                HomeUiStatePsrt.Error
            } catch (e: HttpException) {
                Log.e("PesertaHomeViewModel", "HTTP Exception while fetching patients: ${e.message}")
                HomeUiStatePsrt.Error
            }
        }
    }

    fun deletePeserta(idPeserta: Int) {
        viewModelScope.launch {
            Log.d("PesertaHomeViewModel", "Attempting to delete patient with ID: $idPeserta")
            try {
                pesertaRepo.deletePeserta(idPeserta)
                Log.d("PesertaHomeViewModel", "Successfully deleted patient with ID: $idPeserta")
            } catch (e: IOException) {
                Log.e("PesertaHomeViewModel", "IO Exception while deleting patient: ${e.message}")
                pesertaUIState = HomeUiStatePsrt.Error
            } catch (e: HttpException) {
                Log.e("PesertaHomeViewModel", "HTTP Exception while deleting patient: ${e.message}")
                pesertaUIState = HomeUiStatePsrt.Error
            }
        }
    }
}
