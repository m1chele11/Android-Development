package edu.iu.mbarrant.project8_thor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import edu.iu.mbarrant.project8_thor.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent



class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        //sending email to dev
        val contactButton = findViewById<Button>(R.id.contactbtn)
        contactButton.setOnClickListener {
//            val emailIntent = Intent(Intent.ACTION_SENDTO)
//            emailIntent.data = Uri.parse("mailto:michelebarrantes@gmail.com")
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
//            if (emailIntent.resolveActivity(packageManager) != null) {
//                startActivity(emailIntent)
//            } else {
//                // Handle the case where no email app is available
//                Log.d("mail error", "could not compose email")
//            }
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("michelebarrantes@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Log.d("Mail Error", "Could not compose email")
            }
        }



        //linking adapter to rv
        val movieAdapter = MovieAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.rvMovies)
        recyclerView.adapter = movieAdapter

        val editTextMovieTitle = findViewById<EditText>(R.id.editTextMovieTitle)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)


        //retrofit instance and OMDbAPI
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val omDbApi = retrofit.create(OMDbApi::class.java)

        val apiKey = "d975cba8"  // Replace with your actual API key



        buttonSearch.setOnClickListener {
            // Get the movie title from the EditText
            val movieTitle = editTextMovieTitle.text.toString()

            omDbApi.getMovie(movieTitle, apiKey).enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie == null) {
                            // Handle the case where the response is successful but the movie is null
                            Log.w(
                                TAG,
                                "Did not receive valid response body from Yelp API... exiting"
                            )
                            return
                        }
                        // Update the RecyclerView with the movie data
                        movieAdapter.submitList(listOf(movie))
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    // Handle network errors or request failure
                    Log.i(TAG, "onFailure $t")
                }
            })
        }
    }
}