package edu.ucne.erick_p2_p2.data.remote.remotedatasouce

import edu.ucne.erick_p2_p2.data.remote.api.DepositoManagerApi
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val depositoManagerApi: DepositoManagerApi
){
    suspend fun getDepositos() = depositoManagerApi.getDepositos()

    suspend fun getDeposito(id: Int) = depositoManagerApi.getDeposito(id)

    suspend fun saveDeposito(depositoDto: DepositoDto) = depositoManagerApi.saveDeposito(depositoDto)

    suspend fun actualizarDeposito(id: Int, depositoDto: DepositoDto) = depositoManagerApi.actualizarDeposito(id, depositoDto)

    suspend fun deleteDeposito(id: Int) = depositoManagerApi.deleteDeposito(id)
}