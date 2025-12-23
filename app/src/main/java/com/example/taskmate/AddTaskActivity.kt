package com.example.taskmate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    private lateinit var db: TaskDBHelper
    private var taskId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        db = TaskDBHelper(this)

        val etTitle: EditText = findViewById(R.id.etTitle)
        val etDesc: EditText = findViewById(R.id.etDesc)
        val etDeadline: EditText = findViewById(R.id.etDeadline)
        val btnSave: Button = findViewById(R.id.btnSave)

        // cek apakah mode edit
        taskId = intent.getIntExtra("TASK_ID", -1).takeIf { it != -1 }
        if (taskId != null) {
            etTitle.setText(intent.getStringExtra("TITLE"))
            etDesc.setText(intent.getStringExtra("DESC"))
            etDeadline.setText(intent.getStringExtra("DEADLINE"))
            btnSave.text = "Update"
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val deadline = etDeadline.text.toString()

            if (taskId == null) {
                db.insertTask(title, desc, deadline)
            } else {
                db.updateTask(taskId!!, title, desc, deadline)
            }
            finish()
        }
    }
}
