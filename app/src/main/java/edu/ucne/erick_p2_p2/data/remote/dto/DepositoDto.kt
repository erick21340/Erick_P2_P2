package edu.ucne.erick_p2_p2.data.remote.dto

data class DepositoDto(
    val Depositoid: Int,
    val fecha: String,
    val Cuentaid: Int,
    val concepto: String,
    val monto: Double
)
