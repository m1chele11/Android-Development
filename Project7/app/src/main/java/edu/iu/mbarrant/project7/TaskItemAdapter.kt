package edu.iu.mbarrant.project7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.iu.mbarrant.project7.databinding.TaskItemBinding

class TaskItemAdapter(val clickListener: (task: Task) -> Unit,
                      val deleteClickListener: (taskId: String) -> Unit)
    : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener)
    }

    class TaskItemViewHolder(val binding: TaskItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }

        fun bind(item: Task, clickListener: (task: Task) -> Unit,
                 deleteClickListener: (taskId: String) -> Unit) {
            binding.task = item
            binding.root.setOnClickListener { clickListener(item) }
            binding.deleteButton.setOnClickListener { deleteClickListener(item.taskId!!) }
        }
    }
}