package com.devan.lab.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devan.lab.Adapter.QuestionAdapter
import com.devan.lab.Models.QuizQuestion
import com.devan.lab.R
import com.devan.lab.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity(), QuestionAdapter.OnAnswerSelectedListener {

    private lateinit var binding: ActivityQuizBinding
    private var position: Int = 0
    private var receivedList: MutableList<QuizQuestion> = mutableListOf()
    private var allScore = 0
    private var questionAdapter: QuestionAdapter? = null
    private val userAnswers: MutableMap<Int, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receivedList = intent.getParcelableArrayListExtra<QuizQuestion>("list")!!.toMutableList()
        val moduleId = intent.getIntExtra("module_id", -1)
        val courseId = intent.getIntExtra("course_id", -1)

        Log.d("Questions", "QUESTIONS RECEIVED: $receivedList")

        binding.apply {
            backToModule.setOnClickListener {
                finish()
            }

            progressBar.progress = 1
            questionNumber.text = "Question " + progressBar.progress + "/5"
            questionName.text = receivedList[position].question

            loadAnswers()

            leftArrow.setOnClickListener {
                if (progressBar.progress > 1) {
                    position--
                    progressBar.progress -= 1
                    questionNumber.text = "Question " + progressBar.progress + "/5"
                    questionName.text = receivedList[position].question
                    loadAnswers()
                }
            }

            rightArrow.setOnClickListener {
                if (progressBar.progress < 5) {
                    position++
                    progressBar.progress += 1
                    questionNumber.text = "Question " + progressBar.progress + "/5"
                    questionName.text = receivedList[position].question
                    loadAnswers()
                } else {
                    val stringUserAnswers = userAnswers.mapKeys { it.key.toString() }
                    val intent = Intent(this@QuizActivity, ScoreActivity::class.java)
                    intent.putExtra("Score", allScore)
                    intent.putExtra("module_id", moduleId)
                    intent.putExtra("course_id", courseId)
                    intent.putExtra("userAnswers", HashMap(stringUserAnswers))
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun loadAnswers() {
        val options = receivedList[position].options ?: listOf()
        questionAdapter = QuestionAdapter(receivedList[position].correctAnswer.toString(), options, this)
        binding.questionList.apply {
            layoutManager = LinearLayoutManager(this@QuizActivity)
            adapter = questionAdapter
        }
    }

    override fun onAnswerSelected(answer: String) {
        val currentQuestion = receivedList[position]
        if (answer == currentQuestion.correctAnswer) {
            allScore++
        }
        currentQuestion.clickedAnswer = answer
        userAnswers[currentQuestion.id] = answer

        binding.rightArrow.isEnabled = true
    }
}
