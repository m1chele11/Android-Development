package edu.iu.mbarrant.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Screen3Activity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen3)

        //Getting extras
        val numCorrect = intent.getIntExtra("QuestionsCorrect", 0)
        val numWrong = intent.getIntExtra("QuestionsWrong", 0)
        val numTotal = intent.getIntExtra("QuestionsTotal", 0)

        //Initializing TV's
        val total = findViewById<TextView>(R.id.totalNum)
        val correct = findViewById<TextView>(R.id.correctNum)
        val wrong = findViewById<TextView>(R.id.wrongNum)

        //displaying scores
        total.text = numTotal.toString()
        correct.text = numCorrect.toString()
        wrong.text = numWrong.toString()

        //play again
        val playAgain = findViewById<Button>(R.id.buttonPlayAgain)
        playAgain.setOnClickListener{
            // Create an intent to navigate back to MainActivity (your main menu)
            val intent = Intent(this, MainActivity::class.java)

            // Start the MainActivity
            startActivity(intent)

            // Finish the current activity (Screen3Activity) to remove it from the stack
            finish()
        }
    }
}