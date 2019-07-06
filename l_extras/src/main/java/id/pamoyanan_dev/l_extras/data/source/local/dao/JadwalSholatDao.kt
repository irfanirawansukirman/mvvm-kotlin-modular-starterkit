package id.pamoyanan_dev.l_extras.data.source.local.dao

import android.arch.persistence.room.*
import id.pamoyanan_dev.l_extras.data.model.JadwalSholat

@Dao
interface JadwalSholatDao {

    @Query("SELECT * FROM tb_jadwal_sholat")
    fun getAllJadwalSholat(): List<JadwalSholat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllJadwalSholat(data: List<JadwalSholat>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJadwalSholat(data: JadwalSholat)

    @Query("DELETE FROM tb_jadwal_sholat")
    fun deleteAllAdzan()

}