package com.example.wardrobe.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.wardrobe.database.AppDatabase
import com.example.wardrobe.database.dao.ShirtDao
import com.example.wardrobe.database.dao.TrouserDao
import com.example.wardrobe.database.model.Shirt
import com.example.wardrobe.database.model.Trouser

class Repository(
    application: Application
) {

    private var mShirtDao: ShirtDao
    private var mTrouserDao: TrouserDao

    init {
        val db = AppDatabase.getDatabase(application)
        mShirtDao = db.shirtDao()
        mTrouserDao = db.trouserDao()
    }

    fun getShirtList(): LiveData<List<Shirt>> {
        return mShirtDao.getShirtList()
    }

    fun getRandomShirtList(): LiveData<List<Shirt>>{
        return mShirtDao.getRandomShirtList()
    }

    suspend fun addShirt(shirt: Shirt){
        mShirtDao.addShirt(shirt)
    }

    fun getTrouserList(): LiveData<List<Trouser>> {
        return mTrouserDao.getTrouserList()
    }

    fun getRandomTrouserList(): LiveData<List<Trouser>>{
        return mTrouserDao.getRandomTrouserList()
    }

    suspend fun addTrouser(trouser: Trouser){
        mTrouserDao.addTrouser(trouser)
    }

}