package edu.iu.mbarrant.project1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing the current number as an empty string until one is clicked
        var currentNumber = ""
        //initializing the current operator as an empty string until one is clicked
        var currentOperator = ""
        //the current result is 0.0 until a number is clicked and operator is executed
        var result = 0.0
        // this is used for the clear Button, since a clear will always display 0
        val empty0 = "0"

        //this is the text view
        val resultTV = findViewById<TextView>(R.id.resultTV)

        //an array of button ID's
        val buttonIds = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonPlus, R.id.buttonMinus, R.id.buttonNegate, R.id.buttonDivide, R.id.buttonDecimal,
            R.id.buttonEquals, R.id.buttonClear, R.id.buttonPercent, R.id.buttonEquals, R.id.buttonTimes
        )
        // For loop to loop through the array, that way we don't need multiple click Listeners
        for (buttonId in buttonIds) {

            /*
            here the button selected becomes the button val, we check the button label
            first, that way we know what math to apply to the numbers clicked.
            since it is a string and we have the possibility of having a double we use .todouble()
            once an operator is selected the current number becomes empty again so that the user can select a new number to execut the operation
            we assign the result number to be displayed in the text view
             */
            val button: Button = findViewById(buttonId)
            button.setOnClickListener {
                when (val buttonLabel = button.text) {
                    "+/-" -> {
                        currentNumber = (currentNumber.toDouble() * -1).toString()
                        resultTV.text = currentNumber
                    }
                    "+", "-", "X", "/" -> {
                        currentOperator = buttonLabel.toString()
                        result = currentNumber.toDouble()
                        currentNumber = ""

                    }
                    //check what math to apply by button label
                    "=" -> {
                        if (currentOperator.isNotEmpty() && currentNumber.isNotEmpty()) {
                            when (currentOperator) {
                                "+" -> result += currentNumber.toDouble()
                                "-" -> result -= currentNumber.toDouble()
                                "X" -> result *= currentNumber.toDouble()
                                "/" -> result /= currentNumber.toDouble()
                                "%" -> result = currentNumber.toDouble() / 100
                            }
                            currentNumber = result.toString()
                            resultTV.text = currentNumber
                            currentOperator = ""
                        }
                    }
                    //clear
                    "C" -> {
                        currentNumber = empty0
                        resultTV.text = currentNumber
                        currentNumber =""
                    }
                    //assign to TV
                    else -> {
                        currentNumber += buttonLabel
                        resultTV.text = currentNumber
                    }
                }
            }
        }


    }

}