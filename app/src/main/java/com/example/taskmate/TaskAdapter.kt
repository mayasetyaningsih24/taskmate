package com.example.taskmate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvDeadline: TextView = itemView.findViewById(R.id.tvDeadline)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.tvTitle.text = task.title
        holder.tvDesc.text = task.description
        holder.tvDeadline.text = "Deadline: ${task.deadline}"

        // tombol hapus
        holder.btnDelete.setOnClickListener {
            onDelete(task.id)
        }

        // tombol edit
        holder.btnEdit.setOnClickListener {
            onEdit(task)
        }
    }


    override fun getItemCount(): Int = tasks.size

    fun removeAt(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateList(newList: MutableList<Task>) {
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }
}
