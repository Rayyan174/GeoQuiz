package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val questions = listOf(
        "The Pacific Ocean is the largest ocean on Earth." to true,
        "Australia is in the Northern Hemisphere." to false,
        "The Nile River is located in Africa." to true,
        "Mount Everest is the tallest mountain above sea level." to true,
        "Brazil is located in Europe." to false,
        "The Sahara Desert is in Africa." to true
    )
    private var currentIndex = 0
    private var score = 0
    private var answeredCount = 0
    private val answeredQuestions = BooleanArray(questions.size)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CURRENT_INDEX", 0)
            score = savedInstanceState.getInt("SCORE", 0)
            answeredCount = savedInstanceState.getInt("ANSWERED_COUNT", 0)

            val savedAnswers =
                savedInstanceState.getBooleanArray("ANSWERED_QUESTIONS")

            if (savedAnswers != null) {
                savedAnswers.copyInto(answeredQuestions)
            }
        }
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val cheatButton = findViewById<Button>(R.id.cheatButton)
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        questionTextView.text = questions[currentIndex].first
        scoreTextView.text = "Score: $score / ${questions.size}"
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questions.size
            questionTextView.text = questions[currentIndex].first
        }
        fun checkAnswer(userAnswer: Boolean) {
            val correctAnswer = questions[currentIndex].second

            if (!answeredQuestions[currentIndex]) {
                answeredQuestions[currentIndex] = true
                answeredCount++

                if (userAnswer == correctAnswer) {
                    score++
                }
            }

            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
            }

            scoreTextView.text = "Score: $score / ${questions.size}"

            if (answeredCount == questions.size) {
                Toast.makeText(
                    this,
                    "Final Score: $score / ${questions.size}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra("ANSWER_IS_TRUE", questions[currentIndex].second)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_INDEX", currentIndex)
        outState.putInt("SCORE", score)
        outState.putInt("ANSWERED_COUNT", answeredCount)
        outState.putBooleanArray("ANSWERED_QUESTIONS", answeredQuestions)
    }

}