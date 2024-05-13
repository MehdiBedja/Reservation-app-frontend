// AppDatabase.kt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WilayaEntity::class, DairaEntity::class, CommuneEntity::class, ParkingEntity::class, ParkingPlaceEntity::class, ReservationEntity::class, NotificationEntity::class, CustomUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getParkingDao(): ParkingDao
    abstract fun getWilayaDao(): WilayaDao
    abstract fun getDairaDao(): DairaDao
    abstract fun getCommuneDao(): CommuneDao
    abstract fun getParkingPlaceDao(): ParkingPlaceDao
    abstract fun getReservationDao(): ReservationDao
    abstract fun getNotificationDao(): NotificationDao
    abstract fun getCustomUserDao(): CustomUserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
