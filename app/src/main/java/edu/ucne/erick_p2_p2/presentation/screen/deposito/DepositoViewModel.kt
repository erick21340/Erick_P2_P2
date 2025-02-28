package edu.ucne.erick_p2_p2.presentation.screen.deposito

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.erick_p2_p2.data.local.entitys.Resource
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import edu.ucne.erick_p2_p2.data.remote.repository.DepositoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DepositoViewModel @Inject constructor(
    private val depositoRepository: DepositoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        getDepositos()
    }

    fun save() {
        viewModelScope.launch {
            if (!isValid()) {
                _uiState.update {
                    it.copy(error = "Por favor, completa todos los campos correctamente.", isLoading = true)
                }
                return@launch
            }

            val existe = _uiState.value.depositos.firstOrNull { it.concepto == _uiState.value.concepto }

            if (existe != null) {
                _uiState.update {
                    it.copy(error = "Ya existe un deposito con este concepto.", isLoading = false)
                }
                return@launch
            }

            depositoRepository.save(_uiState.value.toDto()).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        getDepositos()
                        println("Se guardo")
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                        println("No guardo")
                    }
                }
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            depositoRepository.delete(id)
        }
    }

    fun update() {
        viewModelScope.launch {
            depositoRepository.update(
                _uiState.value.idDeposito, DepositoDto(
                    idDeposito = _uiState.value.idDeposito,
                    fecha = _uiState.value.fecha,
                    idCuenta = _uiState.value.idCuenta,
                    concepto = _uiState.value.concepto,
                    monto = _uiState.value.monto
                )
            )
        }
    }

    fun new() {
        _uiState.value = UiState()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onFechaChange(fecha: String) {
        val inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

        val formattedFecha = LocalDate.parse(fecha, inputFormatter)
            .atStartOfDay()
            .format(outputFormatter)

        _uiState.update {
            it.copy(fecha = formattedFecha)
        }
    }

    fun onMontoChange(monto: Double) {
        _uiState.update {
            it.copy(
                monto = monto,
                error = if (monto == 0.0) "Monto inválido" else null
            )
        }
    }

    fun onConceptoChange(concepto: String) {
        _uiState.update {
            it.copy(
                concepto = concepto,
                error = if (concepto.isBlank()) "Debes rellenar el campo Concepto" else null
            )
        }
    }

    fun getById(idDeposito: Int) {
        viewModelScope.launch {
            if (idDeposito > 0) {
                depositoRepository.find(idDeposito).collect { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            val depositoDto = result.data
                            _uiState.update {
                                it.copy(
                                    idDeposito = depositoDto?.idDeposito ?: 0,
                                    concepto = depositoDto?.concepto ?: "",
                                    monto = depositoDto?.monto ?: 0.0,
                                    fecha = depositoDto?.fecha ?: "",
                                    idCuenta = depositoDto?.idCuenta ?: 1
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(error = result.message)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getDepositos() {
        viewModelScope.launch {
            depositoRepository.getDepositos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                depositos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return !(_uiState.value.concepto.isNullOrBlank())
    }
}

fun UiState.toDto() = DepositoDto(
    idDeposito = idDeposito,
    fecha = fecha,
    idCuenta = idCuenta,
    concepto = concepto,
    monto = monto
)

data class UiState(
    val idDeposito: Int = 0,
    val fecha: String = "",
    val idCuenta: Int = 1,
    val concepto: String = "",
    val monto: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val depositos: List<DepositoDto> = emptyList()
)
