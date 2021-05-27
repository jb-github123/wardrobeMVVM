package com.example.wardrobe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wardrobe.database.dao.ShirtDao
import com.example.wardrobe.database.dao.TrouserDao
import com.example.wardrobe.database.model.Shirt
import com.example.wardrobe.database.model.Trouser

@Database(entities = [Shirt::class, Trouser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shirtDao(): ShirtDao
    abstract fun trouserDao(): TrouserDao
    // abstract fun trouserDao()

    companion object {

        @Volatile
        private var db: AppDatabase? = null

        fun getDatabase(applicationContext: Context): AppDatabase {
            return db ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "wardrobeDB"
                ).fallbackToDestructiveMigration().build()
                db = instance
                instance
            }
        }

    }

}
