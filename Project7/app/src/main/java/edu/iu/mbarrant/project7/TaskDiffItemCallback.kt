package edu.iu.mbarrant.project7

import androidx.recyclerview.widget.DiffUtil

class TaskDiffItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task)
            = (oldItem.taskId == newItem.taskId)
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = (oldItem == newItem)
}