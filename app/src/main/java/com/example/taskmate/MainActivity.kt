package com.example.taskmate

import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var db: TaskDBHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = TaskDBHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadTasks()

        val fab: FloatingActionButton = findViewById(R.id.fabAdd)
        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    private fun loadTasks() {
        val tasks = db.getAllTasks()
        val emptyView: TextView = findViewById(R.id.tvEmpty)

        if (tasks.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            adapter = TaskAdapter(
                tasks,
                onDelete = { id ->
                    db.deleteTask(id)
                    loadTasks()
                },
                onEdit = { task ->
                    val intent = Intent(this, AddTaskActivity::class.java)
                    intent.putExtra("TASK_ID", task.id)
                    intent.putExtra("TITLE", task.title)
                    intent.putExtra("DESC", task.description)
                    intent.putExtra("DEADLINE", task.deadline)
                    startActivity(intent)
                }
            )
            recyclerView.adapter = adapter
        }
    }



    override fun onResume() {
        super.onResume()
        loadTasks()
    }
}
