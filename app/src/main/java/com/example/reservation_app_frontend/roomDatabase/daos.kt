// DaoInterfaces.kt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParkingDao {
    @Insert
    fun insertParking(parking: ParkingEntity)

    @Query("SELECT * FROM ParkingEntity")
    fun getAllParking(): List<ParkingEntity>
}

@Dao
interface WilayaDao {
    @Insert
    fun insertWilaya(wilaya: WilayaEntity)

    @Query("SELECT * FROM WilayaEntity")
    fun getAllWilaya(): List<WilayaEntity>
}

@Dao
interface DairaDao {
    @Insert
    fun insertDaira(daira: DairaEntity)

    @Query("SELECT * FROM DairaEntity")
    fun getAllDaira(): List<DairaEntity>
}

@Dao
interface CommuneDao {
    @Insert
    fun insertCommune(commune: CommuneEntity)

    @Query("SELECT * FROM CommuneEntity")
    fun getAllCommune(): List<CommuneEntity>
}

@Dao
interface ParkingPlaceDao {
    @Insert
    fun insertParkingPlace(parkingPlace: ParkingPlaceEntity)

    @Query("SELECT * FROM ParkingPlaceEntity")
    fun getAllParkingPlace(): List<ParkingPlaceEntity>
}

@Dao
interface ReservationDao {
    @Insert
    fun insertReservation(reservation: ReservationEntity)

    @Query("SELECT * FROM ReservationEntity")
    fun getAllReservation(): List<ReservationEntity>
}

@Dao
interface NotificationDao {
    @Insert
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM NotificationEntity")
    fun getAllNotification(): List<NotificationEntity>
}

@Dao
interface CustomUserDao {
    @Insert
    fun insertCustomUser(customUser: CustomUserEntity)

    @Query("SELECT * FROM CustomUserEntity")
    fun getAllCustomUser(): List<CustomUserEntity>
}
