package com.example.wardrobe.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wardrobe.database.model.Shirt

@Dao
interface ShirtDao {
    @Query("SELECT * FROM shirt order by id desc")
    fun getShirtList(): LiveData<List<Shirt>>

    /*@Query("SELECT * FROM shirt WHERE id in (SELECT id from shirt ORDER BY RANDOM())")
    fun getRandomShirtList(): LiveData<List<Shirt>>*/

    @Query("SELECT * FROM shirt ORDER BY RANDOM()")
    fun getRandomShirtList(): LiveData<List<Shirt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShirt(shirt: Shirt)

    /*@Delete
    fun deleteShirt(shirt: Shirt)*/
}
