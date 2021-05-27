package com.example.wardrobe.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wardrobe.database.model.Trouser

@Dao
interface TrouserDao {
    @Query("SELECT * FROM trouser order by id desc")
    fun getTrouserList(): LiveData<List<Trouser>>

    @Query("SELECT * FROM trouser ORDER BY RANDOM()")
    fun getRandomTrouserList(): LiveData<List<Trouser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrouser(trouser: Trouser)

    /*@Delete
    fun deleteTrouser(trouser: Trouser)*/
}
