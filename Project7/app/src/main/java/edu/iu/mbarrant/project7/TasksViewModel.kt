package edu.iu.mbarrant.project7

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TasksViewModel : ViewModel() {
    //using firebase authentication
    private var auth: FirebaseAuth = Firebase.auth

    var user: User = User()
    var verifyPassword = ""
    var taskId: String = ""
    var task = MutableLiveData<Task>()
    private val _tasks: MutableLiveData<MutableList<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>>
        get() = _tasks as LiveData<List<Task>>
    private val _navigateToTask = MutableLiveData<String?>()
    val navigateToTask: LiveData<String?>
        get() = _navigateToTask

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private val _navigateToSignUp = MutableLiveData<Boolean>(false)
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    private val _navigateToSignIn = MutableLiveData<Boolean>(false)
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn


    //using firebase realtime database
    private lateinit var tasksCollection: DatabaseReference

    init {
        if (taskId.trim() == "") {
            task.value = Task()
        }
        _tasks.value = mutableListOf<Task>()
    }

    fun initializeTheDatabaseReference() {

        val database = Firebase.database
        tasksCollection = database
            .getReference("tasks")
            .child(auth.currentUser!!.uid)


        //addValueListner
        tasksCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var tasksList: ArrayList<Task> = ArrayList()
                for (taskSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    var task = taskSnapshot.getValue<Task>()
                    task?.taskId = taskSnapshot.key!!
                    tasksList.add(task!!)
                }
                _tasks.value = tasksList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("database error", "failed to create new task")
            }
        })

    }

    fun getAll(): LiveData<List<Task>> {
        return tasks
    }

    fun updateTask() {
//        initializeTheDatabaseReference()
        if (taskId.trim() == "") {
            tasksCollection.push().setValue(task.value)
        } else {
            tasksCollection.child(taskId).setValue(task.value)
        }
        _navigateToList.value = true
    }

    fun deleteTask(taskId: String) {
        tasksCollection.child(taskId).removeValue()
    }

    fun onTaskClicked(selectedTask: Task) {
        _navigateToTask.value = selectedTask.taskId
        taskId = selectedTask.taskId
        task.value = selectedTask
    }

    fun onNewTaskClicked() {
        _navigateToTask.value = ""
        taskId = "" // Clear the task ID to create a new task
        task.value = Task()

        // Assuming you have a reference to the tasks node
//        tasksCollection.push() // Create a new child node under the tasks node
//            .setValue(task.value) // Set the value of the new task
    }

    fun onTaskNavigated() {
        _navigateToTask.value = null
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }

    fun navigateToSignUp() {
        _navigateToSignUp.value = true
    }

    fun onNavigatedToSignUp() {
        _navigateToSignUp.value = false
    }

    fun navigateToSignIn() {
        _navigateToSignIn.value = true
    }

    fun onNavigatedToSignIn() {
        _navigateToSignIn.value = false
    }

    fun signIn() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                initializeTheDatabaseReference()
                _navigateToList.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

    fun signUp() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        if (user.password != verifyPassword) {
            _errorHappened.value = "Password and verify do not match."
            return
        }
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("User created", "createUserWithEmail:success")
                _navigateToSignIn.value = true
            } else {
                Log.d("User creation Failed", "createUserWithEmail:failure")
                _errorHappened.value = it.exception?.message
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _navigateToSignIn.value = true
    }


    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}