package edu.ucne.erick_p2_p2.data.remote.repository


import android.util.Log
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import edu.ucne.erick_p2_p2.data.remote.remotedatasouce.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class DepositoRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun get(): Flow<Resource<List<DepositoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val depositoList = dataSource.getDepositos()
            emit(Resource.Success(depositoList))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            Log.e("DepositoRepository", "HttpException: $errorMessage")
            emit(Resource.Error("Error de conexión: $errorMessage"))
        } catch (e: Exception) {
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    suspend fun update(id: Int, DepositoDto: DepositoDto) =
        dataSource.actualizarDeposito(id, DepositoDto)

    suspend fun find(id: Int) = dataSource.getDepositos()

    suspend fun save(DepositoDto: DepositoDto) = dataSource.saveDeposito(DepositoDto)

    suspend fun delete(id: Int) = dataSource.deleteDeposito(id)
}