package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.loadExpenses
import com.example.wealthwings.db.removeAllExpense
import com.example.wealthwings.db.removeExpense
import com.example.wealthwings.db.writeExpense
import com.example.wealthwings.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private var currentUserUid: String? = null
    var expensesLiveData = getExpenses()

    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(expensesLiveData) { expenseList ->
            value = expenseList.sumOf { it.amount }
        }
    }
    val totalAmount: LiveData<Double> get() = _totalAmount

    fun setCurrentUser(input: String?) {
        currentUserUid = input
        // Reload expenses for the current user
        expensesLiveData = getExpenses()
        _totalAmount.removeSource(expensesLiveData)
        _totalAmount.addSource(expensesLiveData) { expenses ->
            _totalAmount.value = expenses.sumOf { it.amount }
        }
    }

    fun getExpenses(): LiveData<List<Expense>> {
        return loadExpenses(currentUserUid.toString())
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                writeExpense(currentUserUid.toString(), expense)
            }
        }
    }

    fun deleteExpense(expenseUID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                removeExpense(currentUserUid.toString(), expenseUID)
            }
        }
    }

    fun deleteAllExpenses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                removeAllExpense(currentUserUid.toString())
            }
        }
    }
}
//    private val expenseDao: ExpenseDao = ExpenseDatabase.getInstance(application).getExpenseDao()
//    val expenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()
//    private val _totalAmount = MediatorLiveData<Double>().apply {
//        addSource(expenses) { expenseList ->
//            value = expenseList.sumOf { it.amount }
//        }
//    }
//    val totalAmount: LiveData<Double> get() = _totalAmount
//
//    fun addExpense(expense: Expense) {
//        viewModelScope.launch(Dispatchers.IO) {
//            expenseDao.addExpense(expense)
//        }
//    }
//
//    fun deleteExpense(id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            expenseDao.deleteExpense(id)
//        }
//    }
//
//    fun deleteAllExpenses() {
//        viewModelScope.launch(Dispatchers.IO) {
//            expenseDao.deleteAllExpenses()
//        }
//    }



//@RequiresApi(Build.VERSION_CODES.O)
//class ExpenseViewModel @Inject constructor() : ViewModel() {
//    //private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
//    //val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()
//    val expenseDao = MainApplication.expenseDatabase.getExpenseDao()
//    val expenses : LiveData<List<Expense>> = expenseDao.getAllExpenses()
//    // To track cumulative amount
//    val totalAmount: LiveData<Double> = expenses.map { expenses ->
//        expenses.sumOf { it.amount }
//    }
//        //.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)
//
//    fun addExpense(expense: Expense) {
//        expenseDao.addExpense(expense)
////        viewModelScope.launch {
////            val updatedList = _expenses.value.toMutableList().apply {
////                add(expense)
////            }
////            _expenses.value = updatedList
////        }
//    }
//
//
//
//    // Example function to populate data on app start or for testing
//    init {
//        populateTestData()
//        Log.d("Submit expenses", expenses.toString())
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun populateTestData() {
//        addExpense(Expense(id = "1", amount = 50.0, category = "Food", date = LocalDate.now().toString()))
//        addExpense(Expense(id = "2", amount = 150.0, category = "Utilities", date = LocalDate.now().toString()))
//    }
//}
