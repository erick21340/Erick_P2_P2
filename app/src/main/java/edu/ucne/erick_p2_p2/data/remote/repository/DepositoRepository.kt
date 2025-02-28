package edu.ucne.erick_p2_p2.data.remote.repository


import javax.inject.Inject
import android.util.Log
import edu.ucne.erick_p2_p2.data.local.entitys.Resource
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import edu.ucne.erick_p2_p2.data.remote.remotedatasouce.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


class DepositoRepository @Inject constructor(
    private val dataSource: DataSource
){
    fun getDepositos(): Flow<Resource<List<DepositoDto>>> = flow {
        try{
            emit(Resource.Loading())
            val depositos = dataSource.getDepositos()
            emit(Resource.Success(depositos))
        }catch (e: HttpException){
            val errorMessage = e.response()?.errorBody()?.string()?: e.message()
            Log.e("DepositoRepository", "HttpException: ${e.message}")
            emit(Resource.Error("Error de conexion: ${e.message}"))
        }catch (e: Exception){
            Log.e("DepositoRepository", "Exception: ${e.message}")
            emit(Resource.Error("Error:${e.message}"))
        }
    }


    fun find(id: Int): Flow<Resource<DepositoDto>> = flow {
        try {
            emit(Resource.Loading())
            val depositoR = dataSource.getDeposito(id)
            if (depositoR != null) {
                emit(Resource.Success(depositoR))
            } else {
                emit(Resource.Error("No se encontra el Deposito"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun save(depositoDto: DepositoDto): Flow<Resource<DepositoDto>> = flow {
        try {
            emit(Resource.Loading())
            val depositoR = dataSource.saveDeposito(depositoDto)
            emit(Resource.Success(depositoR))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexion ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    fun update(id: Int, depositoDto: DepositoDto): Flow<Resource<DepositoDto>> = flow {
        try {
            emit(Resource.Loading())
            val depositoR = dataSource.actualizarDeposito(id, depositoDto)
            emit(Resource.Success(depositoR))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexion ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    suspend fun delete(id: Int): Resource<Unit> {
        return try {
            dataSource.deleteDeposito(id)
            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Error de conexion ${e.message()}")
        }
    }
}