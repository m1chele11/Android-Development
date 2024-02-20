package edu.iu.mbarrant.project3

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.Random

class Screen2Activity: AppCompatActivity(){
    //intializing sounds
    private lateinit var correctSound: MediaPlayer
    private lateinit var incorrectSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen2)

        //initialing inside the oncreate
        correctSound = MediaPlayer.create(this, R.raw.correct)
        incorrectSound = MediaPlayer.create(this, R.raw.wrong)

        //operator
        val operatorToSee: String
        //usr textview
        val answerEditText = findViewById<EditText>(R.id.answerText)

        //var three = 3

        //textviews for left and right nums of operator & oppo
        val operatorTV = findViewById<TextView>(R.id.operatorTextView)
        val leftNumTV = findViewById<TextView>(R.id.leftNumberTextView)
        val rightNumTV = findViewById<TextView>(R.id.rightNumberTextView)
        //leftNumTV.text = three.toString()

        // Retrieve intent extras
        val operator = intent.getStringExtra("operator")

        //done button
        val doneButton = findViewById<Button>(R.id.doneButton)

        //checks to see which operator to display
        when (operator) {
            "Addition" -> {
                operatorToSee = "+"
                operatorTV.text = operatorToSee
            }

            "Subtraction" -> {
                operatorToSee = "-"
                operatorTV.text = operatorToSee
            }

            "Multiplication" -> {
                operatorToSee = "X"
                operatorTV.text = operatorToSee
            }

            "Division" -> {
                operatorToSee = "/"
                operatorTV.text = operatorToSee
            }
        }

        val numOfQuestions = intent.getIntExtra("numOfQuestions", 0)
        //getting values from last screen
        val isEasy = intent.getBooleanExtra("isEasy", false)
        val isMedium = intent.getBooleanExtra("isMedium", false)
        val isHard = intent.getBooleanExtra("isHard", false)

        //execute correct difficulty
        if (isEasy) {
            Log.d("Type of game", "In Easy game")

            //initializing R & L in the backend
            var leftNumQ = 0
            var rightNumQ = 0
            var questionsTotal = numOfQuestions //questions total
            var questionCount = 0 //questions asked counter
            val random = Random()//random

            //vars to keep track of number of correct and wrong
            var gotCorrect = 0
            var gotWrong = 0

            //setting first question
            leftNumQ = random.nextInt(10) // Random number between 0 and 9
            rightNumQ = random.nextInt(10) // Random number between 0 and 9
            leftNumTV.text = leftNumQ.toString()
            rightNumTV.text = rightNumQ.toString()

            doneButton.setOnClickListener {
                Log.d("Done clicked", "Done clicked")

                // Retrieve the user's input as a String
                val userAnswerStr = answerEditText.text.toString()
                // Convert to Int or null if not a valid number
                val userAnswer = userAnswerStr.toIntOrNull()

                // Calculate the expected answer for the current question
                val answerExpected = when (operator) {
                    "Addition" -> leftNumQ + rightNumQ
                    "Subtraction" -> leftNumQ - rightNumQ
                    "Multiplication" -> leftNumQ * rightNumQ
                    "Division" -> if (rightNumQ != 0) leftNumQ / rightNumQ else 0 // Ensure rightNum is not 0
                    else -> 0 // Handle unsupported operator
                }

                // Checking if the user's answer is correct or not
                if (userAnswer == answerExpected) {
                    gotCorrect++
                    Log.d("Checking usr ans", "correct!")
                    Log.d("Comparing Ans", "ans expected: $answerExpected usr: $userAnswer")
                    playSound(correctSound) //playing sound
                    showToast("Correct!", Color.GREEN) //changing color of the toast
                } else {
                    gotWrong++
                    Log.d("Checking usr ans", "Incorrect!")
                    playSound(incorrectSound)  //playing sounds
                    showToast("Incorrect!", Color.RED) //changing color of the toast
                }

                if (questionCount < questionsTotal - 1) {
                    // Set up the next question
                    leftNumQ = random.nextInt(10) // Random number between 0 and 9
                    rightNumQ = random.nextInt(10) // Random number between 0 and 9

                    leftNumTV.text = leftNumQ.toString()
                    rightNumTV.text = rightNumQ.toString()

                    answerEditText.text.clear() // Clear user's input

                    Log.d("Question given", "ans expected: $answerExpected")

                    // Increment questionCount after checking the answer
                    questionCount++
                } else {
                    // Create an intent to navigate to Screen 2
                    val intent = Intent(this, MainActivity::class.java)
                    // Add necessary data (difficulty, operator, number of questions) as extras
                    questionsTotal = gotWrong + gotCorrect
                    intent.putExtra("QuestionsCorrect", gotCorrect)
                    intent.putExtra("QuestionsWrong", gotWrong)
                    intent.putExtra("QuestionsTotal", questionsTotal)
                    intent.putExtra("gamePlayed", true)


                    // Start Screen 2
                    startActivity(intent)
                    Log.d(
                        "Opening screen 3",
                        "Correct: $gotCorrect Wrong: $gotWrong total: $questionsTotal"
                    )
                    // Finish the current activity (Screen2Activity) to remove it from the stack

                }
            }
        }
        if (isMedium) {
            Log.d("Type of game", "In Medium game")
            //initializing R & L in the backend
            var leftNumQ = 0
            var rightNumQ = 0
            var questionsTotal = numOfQuestions //questions total
            var questionCount = 0 //questions asked counter
            val random = Random()//random

            //vars to keep track of number of correct and wrong
            var gotCorrect = 0
            var gotWrong = 0

            //setting first question
            leftNumQ = random.nextInt(25) // Random number between 0 and 9
            rightNumQ = random.nextInt(25) // Random number between 0 and 9
            leftNumTV.text = leftNumQ.toString()
            rightNumTV.text = rightNumQ.toString()

            doneButton.setOnClickListener {
                Log.d("Done clicked", "Done clicked")

                // Retrieve the user's input as a String
                val userAnswerStr = answerEditText.text.toString()
                // Convert to Int or null if not a valid number
                val userAnswer = userAnswerStr.toIntOrNull()

                // Calculate the expected answer for the current question
                val answerExpected = when (operator) {
                    "Addition" -> leftNumQ + rightNumQ
                    "Subtraction" -> leftNumQ - rightNumQ
                    "Multiplication" -> leftNumQ * rightNumQ
                    "Division" -> if (rightNumQ != 0) leftNumQ / rightNumQ else 0 // Ensure rightNum is not 0
                    else -> 0 // Handle unsupported operator
                }

                // Checking if the user's answer is correct or not
                if (userAnswer == answerExpected) {
                    gotCorrect++
                    Log.d("Checking usr ans", "correct!")
                    Log.d("Comparing Ans", "ans expected: $answerExpected usr: $userAnswer")
                    playSound(correctSound) //playing sound
                    showToast("Correct!", Color.GREEN) //changing color of the toast
                } else {
                    gotWrong++
                    Log.d("Checking usr ans", "Incorrect!")
                    playSound(incorrectSound)  //playing sounds
                    showToast("Incorrect!", Color.RED) //changing color of the toast
                }

                if (questionCount < questionsTotal - 1) {
                    // Set up the next question
                    leftNumQ = random.nextInt(10) // Random number between 0 and 9
                    rightNumQ = random.nextInt(10) // Random number between 0 and 9

                    leftNumTV.text = leftNumQ.toString()
                    rightNumTV.text = rightNumQ.toString()

                    answerEditText.text.clear() // Clear user's input

                    Log.d("Question given", "ans expected: $answerExpected")

                    // Increment questionCount after checking the answer
                    questionCount++
                } else {
                    // Create an intent to navigate to Screen 2
                    val intent = Intent(this, MainActivity::class.java)
                    // Add necessary data (difficulty, operator, number of questions) as extras
                    questionsTotal = gotWrong + gotCorrect
                    intent.putExtra("QuestionsCorrect", gotCorrect)
                    intent.putExtra("QuestionsWrong", gotWrong)
                    intent.putExtra("QuestionsTotal", questionsTotal)
                    intent.putExtra("gamePlayed", true)


                    // Start Screen 2
                    startActivity(intent)
                    Log.d(
                        "Opening screen 3",
                        "Correct: $gotCorrect Wrong: $gotWrong total: $questionsTotal"
                    )
                    // Finish the current activity (Screen2Activity) to remove it from the stack

                }
            }
        }
        if (isHard) {
            Log.d("Type of game", "In Hard game")
            //initializing R & L in the backend
            var leftNumQ = 0
            var rightNumQ = 0
            var questionsTotal = numOfQuestions //questions total
            var questionCount = 0 //questions asked counter
            val random = Random()//random

            //vars to keep track of number of correct and wrong
            var gotCorrect = 0
            var gotWrong = 0

            //setting first question
            leftNumQ = random.nextInt(50) // Random number between 0 and 9
            rightNumQ = random.nextInt(50) // Random number between 0 and 9
            leftNumTV.text = leftNumQ.toString()
            rightNumTV.text = rightNumQ.toString()

            doneButton.setOnClickListener {
                Log.d("Done clicked", "Done clicked")

                // Retrieve the user's input as a String
                val userAnswerStr = answerEditText.text.toString()
                // Convert to Int or null if not a valid number
                val userAnswer = userAnswerStr.toIntOrNull()

                // Calculate the expected answer for the current question
                val answerExpected = when (operator) {
                    "Addition" -> leftNumQ + rightNumQ
                    "Subtraction" -> leftNumQ - rightNumQ
                    "Multiplication" -> leftNumQ * rightNumQ
                    "Division" -> if (rightNumQ != 0) leftNumQ / rightNumQ else 0 // Ensure rightNum is not 0
                    else -> 0 // Handle unsupported operator
                }

                // Checking if the user's answer is correct or not
                if (userAnswer == answerExpected) {
                    gotCorrect++
                    Log.d("Checking usr ans", "correct!")
                    Log.d("Comparing Ans", "ans expected: $answerExpected usr: $userAnswer")
                    playSound(correctSound) //playing sound
                    showToast("Correct!", Color.GREEN) //changing color of the toast
                } else {
                    gotWrong++
                    Log.d("Checking usr ans", "Incorrect!")
                    playSound(incorrectSound)  //playing sounds
                    showToast("Incorrect!", Color.RED) //changing color of the toast
                }

                if (questionCount < questionsTotal - 1) {
                    // Set up the next question
                    leftNumQ = random.nextInt(10) // Random number between 0 and 9
                    rightNumQ = random.nextInt(10) // Random number between 0 and 9

                    leftNumTV.text = leftNumQ.toString()
                    rightNumTV.text = rightNumQ.toString()

                    answerEditText.text.clear() // Clear user's input

                    Log.d("Question given", "ans expected: $answerExpected")

                    // Increment questionCount after checking the answer
                    questionCount++
                } else {
                    // Create an intent to navigate to Screen 2
                    val intent = Intent(this, MainActivity::class.java)

                    // Add necessary data (difficulty, operator, number of questions) as extras
                    questionsTotal = gotWrong + gotCorrect
                    intent.putExtra("QuestionsCorrect", gotCorrect)
                    intent.putExtra("QuestionsWrong", gotWrong)
                    intent.putExtra("QuestionsTotal", questionsTotal)
                    intent.putExtra("gamePlayed", true)

                    // Start Screen 2
                    startActivity(intent)
                    Log.d(
                        "Sending info to screen 1..",
                        "Correct: $gotCorrect Wrong: $gotWrong total: $questionsTotal"
                    )
                    // Finish the current activity (Screen2Activity) to remove it from the stack

                }
            }
        }
    }
        //function that sets the color and string to the textview
        private fun showToast(message: String, textColor: Int) {
            val toast = findViewById<TextView>(R.id.toast)
            toast.setTextColor(textColor)
            toast.text = message
        }
        //function that plays the toast sounds
        private fun playSound(mediaPlayer: MediaPlayer){
            mediaPlayer.seekTo(0)//Restart sound
            mediaPlayer.start()
        }
}