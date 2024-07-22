package com.example.wealthwings.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wealthwings.model.StockHolding


//@Database(entities = [StockHolding::class], version = 2, exportSchema = false)
//abstract class StockHoldingDatabase : RoomDatabase() {
//
//    abstract fun getStockHoldingDao(): StockHoldingDao
//
//    companion object {
//        const val NAME = "StockHoldingDatabase"
//        @Volatile private var instance: StockHoldingDatabase? = null
//
//        fun getInstance(context: Context): StockHoldingDatabase =
//            instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext,
//                StockHoldingDatabase::class.java, NAME)
//                .fallbackToDestructiveMigration()
//                .build()
//    }
//}

@Database(entities = [StockHolding::class], version = 3, exportSchema = false)
abstract class StockHoldingDatabase : RoomDatabase() {

    abstract fun getStockHoldingDao(): StockHoldingDao

    companion object {
        @Volatile
        private var INSTANCE: StockHoldingDatabase? = null

        fun getInstance(context: Context): StockHoldingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockHoldingDatabase::class.java,
                    "stock_holding_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE StockHolding ADD COLUMN totalPrice DOUBLE NOT NULL DEFAULT 0.0")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the totalPrice column if it's not already present
                database.execSQL("ALTER TABLE StockHolding ADD COLUMN totalPrice DOUBLE NOT NULL DEFAULT 0.0")
            }
        }
    }
}