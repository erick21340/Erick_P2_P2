package edu.ucne.erick_p2_p2.data.remote.remotedatasouce

import edu.ucne.erick_p2_p2.data.remote.api.DepositoManagerApi
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val DepositoManagerApi: DepositoManagerApi
){
    suspend fun getDepositos() = DepositoManagerApi.getDepositos()

    suspend fun get(id: Int) = DepositoManagerApi.getDeposito(id)

    suspend fun saveDeposito(DepositoDto: DepositoDto) = DepositoManagerApi.saveDeposito(DepositoDto)

    suspend fun actualizarDeposito(id: Int, DepositoDto: DepositoDto) = DepositoManagerApi.actualizarDeposito(id, DepositoDto)

    suspend fun deleteDeposito(id: Int) = DepositoManagerApi.deleteDeposito(id)
}