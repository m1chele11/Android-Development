package edu.iu.mbarrant.project9

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import edu.iu.mbarrant.project9.model.Post
import edu.iu.mbarrant.project9.model.User
import edu.iu.mbarrant.project9.databinding.FragmentPostsBinding
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID


class PostsViewModel : ViewModel() {

    val  firestoreDB = FirebaseFirestore.getInstance()

    val TAG = "PostsViewModel"
//    val storageTAG = "https://project9-4409f-default-rtdb.firebaseio.com/" //reference to database
    val storage = Firebase.storage
    val storageRef = storage.reference //storage reference from app

    // Create a child reference
    // imagesRef now points to "images"
   // var imagesRef: StorageReference? = storageRef.child("images")
    // Child references can also take paths
    // spaceRef now points to "images/space.jpg
    // imagesRef still points to "images"
    //var spaceref = storageRef.child("images/$image.jpg") //use picture taken to pass through the professor

    // imagesRef now points to 'images'

    //for displaying data in RV
    private val _postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val postsLiveData: LiveData<List<Post>>
        get() = _postsLiveData

    var signedInUser: User? = null //pass as a parameter
    private val _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
        get() = _posts as LiveData<List<Post>>




    init {

       // val  firestoreDB = FirebaseFirestore.getInstance()
        firestoreDB.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject<User>()
                //was image
                //one more succeess listenr to pop a toast
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }

        var postsReference = firestoreDB
            .collection("posts")
            .limit(30)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects<Post>()
            _posts.value = postList as MutableList<Post>
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }
        }
    }

//    //____ start of displaying photos
//    fun fetchPostsAndUpdateUI() {
//        val firestoreDB = FirebaseFirestore.getInstance()
//
//        val postsReference = firestoreDB
//            .collection("posts")
//            .limit(30)
//            .orderBy("creationTimeMs", Query.Direction.DESCENDING)
//
//        postsReference.addSnapshotListener { snapshot, exception ->
//            if (exception != null || snapshot == null) {
//                // Handle error or empty snapshot
//                return@addSnapshotListener
//            }
//
//            val postList = snapshot.toObjects<Post>()
//            updateRecyclerView(postList)
//        }
//    }
//
//    private fun updateRecyclerView(posts: List<Post>) {
//        _postsLiveData.postValue(posts)
//    }//end of displaying





    // Add a method to handle adding a new post
    fun addPost(post: Post) {
        val currentPosts = _posts.value ?: mutableListOf()
        currentPosts.add(0, post) // Add the new post to the beginning of the list
        _posts.value = currentPosts
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        signedInUser = null

    }
}