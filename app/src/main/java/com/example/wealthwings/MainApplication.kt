package com.example.wealthwings

import android.app.Application
import com.example.wealthwings.db.ExpenseDatabase
import com.example.wealthwings.db.StockHoldingDatabase

//@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        expenseDatabase = ExpenseDatabase.getInstance(this) as ExpenseDatabase
        stockHoldingDatabase = StockHoldingDatabase.getInstance(this) as StockHoldingDatabase
    }

    companion object {
        lateinit var expenseDatabase: ExpenseDatabase
        lateinit var stockHoldingDatabase: StockHoldingDatabase
        lateinit var instance: MainApplication
            private set
    }
}
//    companion object {
//        lateinit var expenseDatabase : ExpenseDatabase
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        Room.databaseBuilder(
//            applicationContext,
//            ExpenseDatabase::class.java,
//            ExpenseDatabase.NAME
//        ).build()
//    }
//}