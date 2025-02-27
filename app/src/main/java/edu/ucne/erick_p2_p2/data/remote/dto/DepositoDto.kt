package edu.ucne.erick_p2_p2.data.remote.dto

data class DepositoDto(
    val idDeposito: Int,
    val fecha: String,
    val idCuenta: Int,
    val concepto: String,
    val monto: Double
)
