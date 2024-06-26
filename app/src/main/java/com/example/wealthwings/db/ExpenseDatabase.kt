package com.example.wealthwings.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wealthwings.model.Expense


@Database(entities = [Expense::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao

    companion object {
        const val NAME = "ExpenseDatabase"
        @Volatile private var instance: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ExpenseDatabase::class.java, NAME)
                .build()
    }
}

//@Database(entities = [Expense::class], version = 1)
//abstract class ExpenseDatabase : RoomDatabase() {
//
//    companion object {
//        const val NAME = "Expense_DB"
//    }
//
//    abstract fun getExpenseDao() : ExpenseDao
//}