package com.example.wealthwings.viewmodels

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.StockHolding
import com.example.wealthwings.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])  // Adjust SDK version as needed
class FirebaseRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FirebaseDB
    private val firebaseDatabase = mockk<FirebaseDatabase>(relaxed = true)
    private val usersReference = mockk<DatabaseReference>(relaxed = true)

    private val auth = mockk<FirebaseAuth>(relaxed = true)
    private val currentUser = mockk<FirebaseUser>(relaxed = true)

    @Before
    fun setUp() {

        mockkStatic(FirebaseAuth::class)
        mockkStatic(FirebaseDatabase::class)
        every { FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/") } returns firebaseDatabase
        every { firebaseDatabase.getReference("users") } returns usersReference
        every { FirebaseAuth.getInstance() } returns auth
        every { auth.currentUser } returns currentUser
        every { currentUser.uid } returns "testUid"

        repository = FirebaseDB()
    }

    @Test
    fun `test writeNewUser`() {
        val uid = "testUid"
        val password = "testPassword"
        val email = "test@example.com"

        repository.writeNewUser(uid, password, email)

        verify { usersReference.child(uid).setValue(User(email, password)) }
    }

    @Test
    fun `test writePassword`() {
        val newPassword = "newPassword"
        val uid = "testUid"

        repository.writePassword(uid, newPassword)

        verify { usersReference.child("testUid").child("password").setValue(newPassword) }
    }

    @Test
    fun `test write email`() {
        val newEmail = "new@example.com"
        val uid = "testUid"

        repository.writeEmail(uid, newEmail)

        verify { usersReference.child("testUid").child("email").setValue(newEmail) }
    }

    @Test
    fun `test delete user`() {
        val uid = "testUid"

        repository.deleteUser(uid)

        verify { usersReference.child(uid).removeValue() }
    }

    @Test
    fun `test writeEmail`() {
        val newEmail = "new@example.com"
        val uid = "testUid"

        repository.writeEmail(uid, newEmail)

        verify { usersReference.child("testUid").child("email").setValue(newEmail) }
    }

    @Test
    fun `test deleteUser`() {
        val uid = "testUid"

        repository.deleteUser(uid)

        verify { usersReference.child(uid).removeValue() }
    }

    @Test
    fun `test writeExpense`() {
        val uid = "testUid"
        val expense = Expense()

        repository.writeExpense(uid, expense)

        verify { usersReference.child(uid).child("expenses").push().setValue(expense) }
    }

    @Test
    fun `test removeExpense`() {
        val uid = "testUid"
        val expenseUID = "expenseUID"

        repository.removeExpense(uid, expenseUID)

        verify { usersReference.child(uid).child("expenses").child(expenseUID).removeValue() }
    }

    @Test
    fun `test removeAllExpense`() {
        val uid = "testUid"

        repository.removeAllExpense(uid)

        verify { usersReference.child(uid).child("expenses").removeValue() }
    }

    @Test
    fun `test readHoldings`() {
        val uid = "testUid"
        val stockHolding1 = StockHolding(
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 100.0,
            quantity = 10,
            totalPrice = 1000.0
        )
        val stockHolding2 = StockHolding(
            symbol = "GOOGL",
            name = "Google Inc.",
            price = 200.0,
            quantity = 5,
            totalPrice = 1000.0
        )

        val stockHoldings = listOf(
            StockHolding(symbol = "AAPL", name = "Apple Inc.", price = 100.0, quantity = 1, totalPrice = 100.0)
        )

        val snapshot = mockk<DataSnapshot>(relaxed = true)

        // Setup mock behavior for DataSnapshot
        every { snapshot.exists() } returns true
        every { snapshot.children } returns stockHoldings.map { stock ->
            mockk<DataSnapshot> {
                every { getValue(StockHolding::class.java) } returns stock
            }
        }.toMutableList()

        // Mock the DatabaseReference and its methods
        val stockHoldingsRef = mockk<DatabaseReference>(relaxed = true)

        // Mock usersReference to return the mock DatabaseReference
        every { usersReference.child(uid).child("stockHoldings") } returns stockHoldingsRef

        // Capture the ValueEventListener
        val valueEventListenerSlot = slot<ValueEventListener>()
        every { stockHoldingsRef.addValueEventListener(capture(valueEventListenerSlot)) } answers {
            // Ensure the Slot is properly initialized by returning a mock object
            mockk()
        }

        // Call the method under test
        val liveData = repository.readHoldings(uid)

        // Trigger onDataChange manually
        valueEventListenerSlot.captured.onDataChange(snapshot)

        // Observe the LiveData
        liveData.observeForever { stockHoldingsResult ->
            // Assert the results
            assertEquals(stockHoldings, stockHoldingsResult)
        }
    }

    @Test
    fun `test writeHolding`() { //upon testing, found out that cannot have stocks with . in the symbol
        val uid = "testUid"
        val stock = StockHolding(
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 100.0,
            quantity = 1,
            totalPrice = (100 * 1).toDouble()
        )
        val existingStock = StockHolding(
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 50.0,
            quantity = 1,
            totalPrice = (50 * 1).toDouble()
        )

        // Mock DataSnapshot to return existingStock
        val snapshot = mockk<DataSnapshot>(relaxed = true)
        every { snapshot.exists() } returns true
        every { snapshot.getValue(StockHolding::class.java) } returns existingStock

        // Mock DatabaseReference behavior
        val stockHoldingRef = mockk<DatabaseReference>(relaxed = true)
        every {
            usersReference.child(uid).child("stockHoldings").child("AAPL").get()
        } returns Tasks.forResult(snapshot)
        every {
            usersReference.child(uid).child("stockHoldings").child("AAPL").setValue(any())
        } returns Tasks.forResult(null)

        // Run the method under test
        repository.writeHolding(uid, stock)

        // Compute the expected StockHolding after update
        val expectedStock = StockHolding(
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 100.0,
            quantity = existingStock.quantity + stock.quantity,
            totalPrice = existingStock.totalPrice + stock.totalPrice
        )

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks()

        // Verify the setValue was called with the correct updated StockHolding
        verify {
            usersReference.child(uid).child("stockHoldings").child("AAPL").setValue(expectedStock)
        }
    }

    @Test
    fun `test removeAllHoldings`() {
        val uid = "testUid"

        repository.removeAllHoldings()

        verify { usersReference.child(uid).child("stockHoldings").removeValue() }
    }
}
