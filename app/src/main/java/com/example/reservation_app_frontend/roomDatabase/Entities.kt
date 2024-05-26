// RoomEntities.kt

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.reservation_app_frontend.data.reservation.ReservationDTO

@Entity(tableName = "ParkingEntity")
data class ParkingEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val commune_name: String,
    val image: String
)

@Entity(tableName = "WilayaEntity")
data class WilayaEntity(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "DairaEntity", foreignKeys = [ForeignKey(entity = WilayaEntity::class, parentColumns = ["id"], childColumns = ["wilaya_id"], onDelete = ForeignKey.CASCADE)])
data class DairaEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val wilaya_id: Int
)

@Entity(tableName = "CommuneEntity", foreignKeys = [ForeignKey(entity = DairaEntity::class, parentColumns = ["id"], childColumns = ["daira_id"], onDelete = ForeignKey.CASCADE)])
data class CommuneEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val daira_id: Int
)

@Entity(tableName = "ParkingPlaceEntity", foreignKeys = [ForeignKey(entity = ParkingEntity::class, parentColumns = ["id"], childColumns = ["parking_id"], onDelete = ForeignKey.CASCADE)])
data class ParkingPlaceEntity(
    @PrimaryKey val id: Int,
    val attributes: String,
    val parking_id: Int
)

@Entity(tableName = "ReservationEntity")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: Int?,
    val parking_place: Int,
    val entry_datetime: String?,
    val exit_datetime: String?,
    val payment_status: String?,
    val reservation_code: String?,
    var isSynced: Boolean = false // Added field for offline insertion tracking
) {
    // Optional constructor to create ReservationEntity from ReservationDTO
    constructor(reservationDTO: ReservationDTO) : this(
        user = reservationDTO.user,
        parking_place = reservationDTO.parking_place,
        entry_datetime = reservationDTO.entry_datetime,
        exit_datetime = reservationDTO.exit_datetime,
        payment_status = reservationDTO.payment_status,
        reservation_code = reservationDTO.reservation_code
    )
}

@Entity(tableName = "NotificationEntity", foreignKeys = [ForeignKey(entity = CustomUserEntity::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE)])
data class NotificationEntity(
    @PrimaryKey val id: Int,
    val user_id: Int,
    val message: String,
    val notificationDate: String?
)

@Entity(tableName = "CustomUserEntity")
data class CustomUserEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val email: String,
    val profileImage: String?
)
