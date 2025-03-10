package edu.ucne.erick_p2_p2.data.remote.api


import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface DepositoManagerApi{
    @Headers("X-API-Key:test")
    @GET("api/Depositos")
    suspend fun getDepositos(): List<DepositoDto>

    @Headers("X-API-Key:test")
    @GET("api/Depositos/{id}")
    suspend fun getDeposito(@Path("id")id: Int): DepositoDto

    @Headers("X-API-Key:test")
    @POST("api/Depositos")
    suspend fun saveDeposito(@Body depositoDto: DepositoDto?): DepositoDto

    @Headers("X-API-Key:test")
    @PUT("api/Depositos/{id}")
    suspend fun actualizarDeposito(
        @Path("id")entityId: Int,
        @Body entity: DepositoDto
    ): DepositoDto

    @Headers("X-API-Key:test")
    @DELETE("api/Depositos/{id}")
    suspend fun deleteDeposito(@Path("id")id: Int): ResponseBody
}