package edu.iu.mbarrant.project8_thor

import androidx.recyclerview.widget.DiffUtil
import edu.iu.mbarrant.project8_thor.model.Movie

class MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Compare unique identifiers of movies, for example, an ID if available
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Compare all relevant attributes of movies
        return oldItem.title == newItem.title &&
                oldItem.year == newItem.year &&
                oldItem.director == newItem.director
        // Compare other relevant attributes here
    }

}
