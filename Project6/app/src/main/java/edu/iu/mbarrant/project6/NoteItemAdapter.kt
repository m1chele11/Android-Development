package edu.iu.mbarrant.project6


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.iu.mbarrant.project6.databinding.NoteItemBinding


class TaskItemAdapter(val clickListener: (taskId: Long) -> Unit)
    : ListAdapter<Note, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TaskItemViewHolder(val binding: NoteItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
        fun bind(item: Note, clickListener: (taskId: Long) -> Unit) {
            binding.note = item
            binding.root.setOnClickListener { clickListener(item.noteId) }
            // Add a log statement here
            Log.d("TaskItemAdapter", "Item Title: ${item.noteName}")
        }
    }
}