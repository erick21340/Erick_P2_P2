package edu.ucne.erick_p2_p2.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

class DepositoEntity {

    @Entity(tableName = "Depositos")
    data class DepositosEntity(
        @PrimaryKey(autoGenerate = true)
        val Depositoid: Int,
        val fecha: String,
        val Cuentaid: Int,
        val concepto: String,
        val monto: Double
    )
}