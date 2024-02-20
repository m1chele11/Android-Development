package edu.iu.mbarrant.project8_thor

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.iu.mbarrant.project8_thor.databinding.MovieItemBinding
import edu.iu.mbarrant.project8_thor.model.Movie


class MovieAdapter(private val context: Context) :
    ListAdapter<Movie, MovieAdapter.ItemViewHolder>(MovieDiffItemCallback()) {
    private val movies = mutableListOf<Movie>()
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }


    class ItemViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun bind(movie: Movie, context: Context) {
            binding.titleTV.text = movie.title
            binding.yearTV.text = movie.year
            binding.directorTV.text = movie.director
            binding.runtimeTV.text = movie.runtime
            binding.genreTV.text = movie.genre
            binding.ratingsTV.text = movie.imdbRating

            // Set a click listener for the "Visit" button
            binding.visitbtn.setOnClickListener {
                openOmdbWebsite(movie.imdbID, context)
            }

            // Set a click listener for the "Share" button
            binding.sharebtn.setOnClickListener {
                shareMovie(context, movie.title, movie.imdbID)
            }


            // Use Glide or another image loading library to load the movie poster
            Glide.with(context)
                .load(movie.posterUrl)
                .into(binding.imageView)

            // Add binding for other views as needed
        }

        //function to visit website
        private fun openOmdbWebsite(imdbID: String, context: Context) {
//            val omdbUrl = "https://www.imdb.com/title/$imdbID/"
//            Log.d("OMDB URL", omdbUrl) // Add this line for debugging
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(omdbUrl))
//            if (intent.resolveActivity(context.packageManager) != null) {
//                context.startActivity(intent)
//            } else {
//                Log.d("OMDB Link Error", "No application can handle this action")
//            }
            val omdbUrl = "https://www.imdb.com/title/$imdbID/"
            Log.d("OMDB URL", omdbUrl) // Add this line for debugging
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(omdbUrl)
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.d("OMDB Link Error", "No application can handle this action")
            }
        }

        //function to share movie
        private fun shareMovie(context: Context, title: String, imdbID: String) {
            val omdbUrl = "https://www.omdb.com/title/$imdbID/"
            val shareText = "Check out the movie '$title' on OMDB: $omdbUrl"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, null))
        }


    }
}