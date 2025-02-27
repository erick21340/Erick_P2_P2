package edu.ucne.erick_p2_p2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.erick_p2_p2.data.local.entitys.DepositoEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface DepositoDao {
    @Upsert
    suspend fun save(listaDeposito: List<DepositoEntity>)

    @Query(
        """
            Select * From Depositos 
            Where DepositoId = :DepositoId
            Limit 1
        """
    )
    suspend fun find(DepositoId: Int): DepositoEntity?

    @Delete
    suspend fun delete(Deposito: DepositoEntity)

    @Query("Select * From Depositos")
    fun getAll(): Flow<List<DepositoEntity>>

}

