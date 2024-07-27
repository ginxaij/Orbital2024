package com.example.wealthwings.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private var currentUserUid: String? = null

    private val _startDate = MutableLiveData<LocalDate?>()
    val startDate: LiveData<LocalDate?> get() = _startDate

    private val _endDate = MutableLiveData<LocalDate?>()
    val endDate: LiveData<LocalDate?> get() = _endDate

//    private var _startDate: MutableLiveData<LocalDate?>()
//    val startDate: LiveData<LocalDate?>? get() = _startDate
//
//    private var _endDate: MutableLiveData<LocalDate?>()
//    val endDate: LiveData<LocalDate?>? get() = _endDate

    var expensesLiveData = getExpenses(startDate?.value, endDate?.value)

    private val _totalAmount = MediatorLiveData<Double>().apply {
        addSource(expensesLiveData) { expenseList ->
            value = expenseList.sumOf { it.amount }
        }
    }
    val totalAmount: LiveData<Double> get() = _totalAmount

    fun setStartAndEndDate(startDateIn: LocalDate?, endDateIn: LocalDate?) {
        _startDate?.value = startDateIn // Set new startDate value
        _endDate?.value = endDateIn // Set new endDate value

        expensesLiveData = getExpenses(startDateIn, endDateIn)
        _totalAmount.removeSource(expensesLiveData)
        _totalAmount.addSource(expensesLiveData) { expenses ->
            _totalAmount.value = expenses.sumOf { it.amount }
        }
    }

    fun setCurrentUser(input: String?) {
        currentUserUid = input
        // Reload expenses for the current user
        expensesLiveData = getExpenses(startDate?.value, endDate?.value)
        _totalAmount.removeSource(expensesLiveData)
        _totalAmount.addSource(expensesLiveData) { expenses ->
            _totalAmount.value = expenses.sumOf { it.amount }
        }
    }

    fun getExpenses(startDate: LocalDate?, endDate: LocalDate?): LiveData<List<Expense>> {
        return FirebaseDB().loadExpenses(currentUserUid.toString(), startDate, endDate)
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                FirebaseDB().writeExpense(currentUserUid.toString(), expense)
            }
        }
    }

    fun deleteExpense(expenseUID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                FirebaseDB().removeExpense(currentUserUid.toString(), expenseUID)
            }
        }
    }

    fun deleteAllExpenses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                FirebaseDB().removeAllExpense(currentUserUid.toString())
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
