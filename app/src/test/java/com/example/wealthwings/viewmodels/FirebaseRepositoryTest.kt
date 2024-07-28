package com.example.wealthwings.viewmodels

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.wealthwings.Screens.UserService
import com.example.wealthwings.Screens.UserService.loginUser
import com.example.wealthwings.Screens.UserService.registerUser
import com.example.wealthwings.db.FirebaseDB
import com.example.wealthwings.model.Expense
import com.example.wealthwings.model.StockHolding
import com.example.wealthwings.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
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
    var mockTask = mockk<Task<AuthResult>>(relaxed = true)
    var mockReauthTask = mockk<Task<Void>>(relaxed = true)
    var mockUpdateTask = mockk<Task<Void>>(relaxed = true)
    var mockEmailUpdateTask = mockk<Task<Void>>(relaxed = true)

    @Before
    fun setUp() {

        mockkStatic(FirebaseAuth::class)
        mockkStatic(FirebaseDatabase::class)

        every { auth.signInWithEmailAndPassword(any(), any()) } returns mockTask
        every { auth.createUserWithEmailAndPassword(any(), any()) } returns mockTask
        every { mockTask.isSuccessful } returns true
        every { FirebaseDatabase.getInstance("https://wealthwings-dca6b-default-rtdb.asia-southeast1.firebasedatabase.app/") } returns firebaseDatabase
        every { firebaseDatabase.getReference("users") } returns usersReference
        every { FirebaseAuth.getInstance() } returns auth
        every { auth.currentUser } returns currentUser
        every { currentUser.uid } returns "testUid"
        every { currentUser.email } returns "test@example.com"
        //every { EmailAuthProvider.getCredential(any(), any()) } returns mockCredential
        every { currentUser.reauthenticate(any()) } returns mockReauthTask
        every { currentUser.updatePassword(any()) } returns mockUpdateTask
        every { currentUser.verifyBeforeUpdateEmail(any()) } returns mockEmailUpdateTask

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
            stockHolding1, stockHolding2
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

    @Test
    fun `loginUser should call onSuccess on successful login`() {
        val email = "test@example.com"
        val password = "password"

        every { mockTask.isSuccessful } returns true
        every { mockTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(mockTask)
            mockTask
        }

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        loginUser(email, password, onSuccess, onError)

        verify { onSuccess.invoke() }
        verify(exactly = 0) { onError.invoke(any()) }
    }

    @Test
    fun `loginUser should call onError on failed login`() {
        val email = "test@example.com"
        val password = "password"
        val exception = FirebaseAuthException("ERROR", "Login failed")

        every { mockTask.isSuccessful } returns false
        every { mockTask.exception } returns exception
        every { mockTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(mockTask)
            mockTask
        }

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        loginUser(email, password, onSuccess, onError)

        verify { onError.invoke("Login failed") }
        verify(exactly = 0) { onSuccess.invoke() }
    }

    @Test
    fun `registerUser should call onSuccess on successful registration`() {
        val email = "test@example.com"
        val password = "password"

        every { mockTask.isSuccessful } returns true
        every { currentUser.uid } returns "userId"
        every { mockTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(mockTask)
            mockTask
        }

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        registerUser(email, password, onSuccess, onError)

        verify { onSuccess.invoke() }
        verify(exactly = 0) { onError.invoke(any()) }
    }

    @Test
    fun `registerUser should call onError on failed registration`() {
        val email = "test@example.com"
        val password = "password"
        val exception = FirebaseAuthException("ERROR", "Registration failed")

        every { mockTask.isSuccessful } returns false
        every { mockTask.exception } returns exception
        every { mockTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<AuthResult>
            listener.onComplete(mockTask)
            mockTask
        }

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        registerUser(email, password, onSuccess, onError)

        verify { onError.invoke("Registration failed") }
        verify(exactly = 0) { onSuccess.invoke() }
    }

    @Test
    fun `changeUserPassword should call callback with success on successful password update`() {
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"

        every { mockReauthTask.isSuccessful } returns true
        every { mockUpdateTask.isSuccessful } returns true
        every { mockReauthTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(mockReauthTask)
            mockReauthTask
        }
        every { mockUpdateTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(mockUpdateTask)
            mockUpdateTask
        }

        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserPassword(email = auth.currentUser!!.email.toString(), currentPassword = currentPassword, newPassword = newPassword, callback = callback)

        verify { callback(true, "Password updated successfully.") }
    }

    @Test
    fun `changeUserPassword should call callback with error on failed reauthentication`() {
        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val exception = FirebaseAuthException("ERROR", "Re-authentication failed")

        every { mockReauthTask.isSuccessful } returns false
        every { mockReauthTask.exception } returns exception
        every { mockReauthTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(mockReauthTask)
            mockReauthTask
        }

        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserPassword(email = auth.currentUser!!.email.toString(), currentPassword = currentPassword, newPassword = newPassword, callback = callback)

        verify { callback(false, "Re-authentication failed: ${exception.message}") }
    }

    @Test
    fun `changeUserEmail should call callback with success on successful email update`() {
        val newEmail = "new@example.com"

        every { mockEmailUpdateTask.isSuccessful } returns true
        every { mockEmailUpdateTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(mockEmailUpdateTask)
            mockEmailUpdateTask
        }

        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserEmail(newEmail, callback)

        verify { callback(true, "Email sent.") }
    }

    @Test
    fun `changeUserEmail should call callback with error on failed email update`() {
        val newEmail = "new@example.com"
        val exception = FirebaseAuthException("ERROR", "Error updating email")

        every { mockEmailUpdateTask.isSuccessful } returns false
        every { mockEmailUpdateTask.exception } returns exception
        every { mockEmailUpdateTask.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(mockEmailUpdateTask)
            mockEmailUpdateTask
        }

        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserEmail(newEmail, callback)

        verify { callback(false, "Error updating email: ${exception.message}") }
    }

    @Test
    fun `changeUserPassword should call callback with error if no authenticated user is found`() {
        every { auth.currentUser } returns null

        val currentPassword = "currentPassword"
        val newPassword = "newPassword"
        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserPassword(email = auth.currentUser?.email.toString(), currentPassword = currentPassword, newPassword =  newPassword, callback =  callback)

        verify { callback(false, "No authenticated user found.") }
    }

    @Test
    fun `changeUserEmail should call callback with error if user is not authenticated`() {
        every { auth.currentUser } returns null

        val newEmail = "new@example.com"
        val callback = mockk<(Boolean, String) -> Unit>(relaxed = true)

        UserService.changeUserEmail(newEmail, callback)

        verify { callback(false, "User is not authenticated") }
    }

}
