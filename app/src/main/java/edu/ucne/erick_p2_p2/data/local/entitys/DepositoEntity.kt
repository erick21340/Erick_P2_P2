package edu.ucne.erick_p2_p2.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey



    @Entity(tableName = "Depositos")
    data class DepositoEntity(
        @PrimaryKey(autoGenerate = true)
        val idDeposito: Int,
        val fecha: String,
        val idCuenta: Int,
        val concepto: String,
        val monto: Double
    )
