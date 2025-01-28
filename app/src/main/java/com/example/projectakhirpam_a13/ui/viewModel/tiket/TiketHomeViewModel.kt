package com.example.loket.ui.viewModel.tiket



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.projectakhirpam_a13.model.Tiket
import com.example.projectakhirpam_a13.repository.TiketRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeTktUiState{
    data class Success(val monitoring: List<Tiket>): HomeTktUiState()
    object Error: HomeTktUiState()
    object Loading: HomeTktUiState()

}

class HomeTktViewModel(
    private val TKT: TiketRepository
): ViewModel() {
    var TKTUiState: HomeTktUiState by mutableStateOf(HomeTktUiState.Loading)
        private set

    init {
        getTKT()
    }

    fun getTKT() {
        viewModelScope.launch {
            TKTUiState = HomeTktUiState.Loading
            TKTUiState = try {
                HomeTktUiState.Success(TKT.getTiket().data)
            } catch (e: IOException) {
                HomeTktUiState.Error

            } catch (e: HttpException) {
                HomeTktUiState.Error
            }
        }
    }

    fun deleteTKT(id_Tiket: Int) {
        viewModelScope.launch {
            try {
                TKT.deleteTiket(id_Tiket)
            } catch (e: IOException) {
                HomeTktUiState.Error
            } catch (e: HttpException) {
                HomeTktUiState.Error
            }
        }
    }
}