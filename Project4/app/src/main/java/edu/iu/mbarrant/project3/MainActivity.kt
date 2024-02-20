package edu.iu.mbarrant.project3

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Recieve values from screen 2
        var numCorrect = intent.getIntExtra("QuestionsCorrect", 0)
        var numTotal = intent.getIntExtra("QuestionsTotal", 0)
        val gamePlayed = intent.getBooleanExtra("gamePlayed", false)
        var display = ""

        if(gamePlayed){
            val percent = numCorrect.toDouble() / numTotal //converting to double
            if (percent >= .8){
                display = "You got $numCorrect out of $numTotal . Good Work!"
                showResult(display, Color.GREEN)
                Log.d("showing correct menu toast", "got $numCorrect out of $numTotal")
            }else{
                display = "You got $numCorrect out of $numTotal . Need to Practice!"
                showResult(display, Color.RED)
                Log.d("showing Incorrect menu toast", "got $numCorrect out of $numTotal")
            }
        }else{
                display = ""
            Log.d("no game played", "no result to display")
        }

        var selectedDifficulty = "" // Initialize with default values
        var selectedOperator = "" // Initialize with default values
        var numofQuestions = 1 // Initialize with default values
        val resultTV = findViewById<TextView>(R.id.numofQuestions)

        //array for + and - questions:
        val numQuestions = arrayOf(R.id.addQuestions, R.id.lessQuestions)

        val difficultyRadioGroup = findViewById<RadioGroup>(R.id.difficultyRadioGroup)
        val operationRadioGroup = findViewById<RadioGroup>(R.id.operatorRadioGroup)

        //radio group for the difficulty settings
        difficultyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Handle difficulty level selection
            when (checkedId) {
                R.id.startEasy -> {
                    // Easy difficulty selected
                    selectedDifficulty = "Easy"
                    Log.d("Difficulty Activity", "Button Clicked: start Ez")
                }
                R.id.startMedium -> {
                    // Medium difficulty selected
                    selectedDifficulty = "Medium"
                    Log.d("Difficulty Activity", "Button Clicked: start Med")
                }
                R.id.startHard -> {
                    // Hard difficulty selected
                    selectedDifficulty = "Hard"
                    Log.d("Difficulty Activity", "Button Clicked: start Hrd")
                }
            }
        }
        //radio group for the operation settings
        operationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Handle operation selection
            when (checkedId) {
                R.id.startAddition -> {
                    // Addition operation selected
                    selectedOperator = "Addition"
                    Log.d("Operator Activity", "Button Clicked: start +")
                }
                R.id.startDivision -> {
                    // Division operation selected
                    selectedOperator = "Division"
                    Log.d("Operator Activity", "Button Clicked: start /")
                }
                R.id.startMultiplication -> {
                    // Multiplication operation selected
                    selectedOperator = "Multiplication"
                    Log.d("Operator Activity", "Button Clicked: start *")
                }
                R.id.startSubtract -> {
                    // Subtraction operation selected
                    selectedOperator = "Subtraction"
                    Log.d("Operator Activity", "Button Clicked: start -")
                }
            }
        }
        //for loop for the + and - buttons to allow user to decide how many
        // questions they want
        for (buttonNumQuestions in numQuestions){
            val button:Button = findViewById(buttonNumQuestions)
            button.setOnClickListener{
                when(button.text){
                    "+" -> {
                        numofQuestions++
                        resultTV.text = numofQuestions.toString()
                        Log.d("QuestionsActivity", "Button Clicked: +")
                        Log.d("QuestionsActivity", "numofQuestions: $numofQuestions")
                        Log.d("QuestionsActivity", "resultTV.text: ${resultTV.text}")
                    }
                    "-" -> {
                        if (numofQuestions == 1){
                            numofQuestions = 1
                            resultTV.text = numofQuestions.toString()
                            Log.d("QuestionsActivity", "Button Clicked: - but already at 1")
                            Log.d("QuestionsActivity", "numofQuestions: $numofQuestions")
                            Log.d("QuestionsActivity", "resultTV.text: ${resultTV.text}")
                        }else{
                            numofQuestions--
                            resultTV.text = numofQuestions.toString()
                            Log.d("QuestionsActivity", "Button Clicked: -")
                            Log.d("QuestionsActivity", "numofQuestions: $numofQuestions")
                            Log.d("QuestionsActivity", "resultTV.text: ${resultTV.text}")
                        }
                    }
                }
            }
        }

        val startButton = findViewById<Button>(R.id.buttonStart)
        startButton.setOnClickListener {
            // Create an intent to navigate to Screen 2
            val intent = Intent(this, Screen2Activity::class.java)

            //checking difficulty
            var isEasy = false
            var isMed = false
            var isHard = false

            when (val difficulty = selectedDifficulty) {
                "Easy" -> { isEasy = true}
                "Medium" -> {isMed = true }
                "Hard" -> { isHard = true}
                else -> {
                    isEasy = true
                }
            }
            intent.putExtra("isEasy", isEasy)
            intent.putExtra("isMedium", isMed)
            intent.putExtra("isHard", isHard)


            intent.putExtra("operator", selectedOperator)
            intent.putExtra("numOfQuestions", numofQuestions)

            // Start Screen 2
            startActivity(intent)
        }
    }
    //function that sets the color and string of the textview
    private fun showResult(message: String, textColor: Int) {
        val gameResult = findViewById<TextView>(R.id.gameResults)//Prev game textview
        gameResult.setTextColor(textColor)
        gameResult.text = message
    }
}
