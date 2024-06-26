package com.example.wealthwings.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wealthwings.model.StockHolding


@Database(entities = [StockHolding::class], version = 2)
abstract class StockHoldingDatabase : RoomDatabase() {

    abstract fun getStockHoldingDao(): StockHoldingDao

    companion object {
        const val NAME = "StockHoldingDatabase"
        @Volatile private var instance: StockHoldingDatabase? = null

        fun getInstance(context: Context): StockHoldingDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                StockHoldingDatabase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}