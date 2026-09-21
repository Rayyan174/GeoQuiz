package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)
        val answerTextView = findViewById<TextView>(R.id.answerTextView)
        val showAnswerButton = findViewById<Button>(R.id.showAnswerButton)
        val answerIsTrue = intent.getBooleanExtra("ANSWER_IS_TRUE", false)
        showAnswerButton.setOnClickListener {
            if (answerIsTrue) {
                answerTextView.text = "TRUE"
            } else {
                answerTextView.text = "FALSE"
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}