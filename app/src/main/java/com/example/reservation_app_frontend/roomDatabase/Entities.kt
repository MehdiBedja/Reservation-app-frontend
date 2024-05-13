// RoomEntities.kt

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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

@Entity(tableName = "ReservationEntity", foreignKeys = [ForeignKey(entity = CustomUserEntity::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = ParkingPlaceEntity::class, parentColumns = ["id"], childColumns = ["parking_place_id"], onDelete = ForeignKey.CASCADE)])
data class ReservationEntity(
    @PrimaryKey val id: Int,
    val user_id: Int,
    val parking_place_id: Int?,
    val reservation_date: String?,
    val entry_datetime: String?,
    val exit_datetime: String?,
    val payment_status: String?,
    val reservation_code: String?
)

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
