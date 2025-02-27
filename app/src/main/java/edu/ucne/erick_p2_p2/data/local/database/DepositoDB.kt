package edu.ucne.erick_p2_p2.data.local.database
import edu.ucne.erick_p2_p2.data.local.dao.DepositoDao
import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.erick_p2_p2.data.local.entitys.DepositoEntity


@Database(
    entities = [
        DepositoEntity::class,

    ],
    version = 1,
    exportSchema = false
)
abstract class  DepositosDB : RoomDatabase() {
  abstract fun DepositosDao(): DepositoDao

}