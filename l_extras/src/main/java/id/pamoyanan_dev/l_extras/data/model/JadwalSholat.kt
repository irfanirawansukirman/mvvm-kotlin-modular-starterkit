package id.pamoyanan_dev.l_extras.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tb_jadwal_sholat")
data class JadwalSholat(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "original_time")
    val originalTime: String,
    @ColumnInfo(name = "time_long")
    val timeLong: Long,
    @ColumnInfo(name = "is_sound")
    val isSound: Boolean,
    @ColumnInfo(name = "original_date")
    val originalDate: String,
    @ColumnInfo(name = "hijr_date")
    val hijrData: String
)