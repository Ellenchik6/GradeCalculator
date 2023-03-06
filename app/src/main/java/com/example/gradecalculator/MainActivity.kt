package com.example.gradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var addButtonClickCounter = 0
    private var hwArr = arrayOfNulls<String>(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homework = findViewById<EditText>(R.id.hwInputID)
        val hwView = findViewById<TextView>(R.id.hwInputViewID)
        val calculateBtn = findViewById<Button>(R.id.calculateButtonID)
        val add = findViewById<Button>(R.id.addID)
        val resetValuesBtn = findViewById<Button>(R.id.resetButtonID)
        val output = findViewById<TextView>(R.id.finalGradeID)
        hwView.visibility = View.INVISIBLE
        output.visibility = View.INVISIBLE


        resetValuesBtn.setOnClickListener {
            hwArr = arrayOfNulls<String>(5)
            addButtonClickCounter = 0
            add.visibility = View.VISIBLE
            hwView.text = ""
            hwView.visibility = View.INVISIBLE
            homework.isEnabled = true
        }

        calculateBtn.setOnClickListener {
            var total = 0
            var hwAverage = 0

            for (i in 0..4) {
                total += hwArr[i]?.toInt() ?: 0
            }
            hwAverage = total / 5

            val midterm1 = findViewById<EditText>(R.id.mt1InputID)
            val midterm2 = findViewById<EditText>(R.id.mt2InputID)
            val participation = findViewById<EditText>(R.id.participationInputID)
            val groupPresentation = findViewById<EditText>(R.id.groupPresentationInputID)
            val finalProject = findViewById<EditText>(R.id.finalProjectInputID)

            val mt1Input = midterm1.text.toString()
            val mt2Input = midterm2.text.toString()
            val participationInput = participation.text.toString()
            val groupPresentationInput = groupPresentation.text.toString()
            val finalProjectInput = finalProject.text.toString()


            if (mt1Input.isBlank() || mt2Input.isBlank() || participationInput.isBlank() ||
                groupPresentationInput.isBlank() || finalProjectInput.isBlank()) {
                output.visibility = View.INVISIBLE
                Toast.makeText(this, "please enter inputs in all fields!", Toast.LENGTH_SHORT).show()
            }
            else if (mt1Input.toInt() < 0 || mt1Input.toInt() > 100 ||
                mt2Input.toInt() < 0 || mt2Input.toInt() > 100 ||
                participationInput.toInt() < 0 || participationInput.toInt() > 100 ||
                groupPresentationInput.toInt() < 0 || groupPresentationInput.toInt() > 100 ||
                finalProjectInput.toInt() < 0 || finalProjectInput.toInt() > 100) {
                output.visibility = View.INVISIBLE
                Toast.makeText(this, "please enter a valid input!", Toast.LENGTH_SHORT).show()
            }
            else {
                if (hwArr[0] == null || hwArr[1] == null || hwArr[2] == null || hwArr[3] == null || hwArr[4] == null) {
                    output.visibility = View.INVISIBLE
                    Toast.makeText(this, "please enter all 5 homeworks in the field!", Toast.LENGTH_SHORT).show()
                }
                else {
                    val finalOutput = gradeCalculate(hwAverage, participationInput, groupPresentationInput, mt1Input, mt2Input, finalProjectInput)
                    output.text = "Final Grade: $finalOutput"
                    output.visibility = View.VISIBLE
                }
            }
        }


        add.setOnClickListener {
            if (hwArr[3] != null){
                hwArr[addButtonClickCounter] = homework.text.toString()
                homework.text.clear()
                hwView.text = "Homework1: ${hwArr[0]}" +
                        " \nHomework2: ${hwArr[1]}" +
                        " \nHomework3: ${hwArr[2]}" +
                        " \nHomework4: ${hwArr[3]}" +
                        " \nHomework5: ${hwArr[addButtonClickCounter]}"
                addButtonClickCounter++
                add.visibility = View.GONE
                homework.isEnabled = false
            } else {
                hwArr[addButtonClickCounter] = homework.text.toString()
                homework.text.clear()
                hwView.text = "Homework1: ${hwArr[addButtonClickCounter]}"
                if (addButtonClickCounter == 1) {
                    hwView.text = "Homework1: ${hwArr[0]}" +
                            " \nHomework2: ${hwArr[addButtonClickCounter]}"
                } else if (addButtonClickCounter == 2) {
                    hwView.text = "Homework1: ${hwArr[0]}" +
                            " \nHomework2: ${hwArr[1]}" +
                            " \nHomework3: ${hwArr[addButtonClickCounter]}"
                } else if (addButtonClickCounter == 3) {
                    hwView.text = "Homework1: ${hwArr[0]}" +
                            " \nHomework2: ${hwArr[1]}" +
                            " \nHomework3: ${hwArr[2]}" +
                            " \nHomework3: ${hwArr[addButtonClickCounter]}"
                }
                hwView.visibility = View.VISIBLE
                addButtonClickCounter++
            }

            if (hwArr[addButtonClickCounter - 1].isNullOrBlank()) {
                hwArr = arrayOfNulls<String>(5)
                addButtonClickCounter = 0
                homework.isEnabled = true
                add.visibility = View.VISIBLE
                hwView.text = ""
                hwView.visibility = View.INVISIBLE
                output.visibility = View.INVISIBLE
                Toast.makeText(this, "please enter valid inputs for all 5 homeworks!", Toast.LENGTH_SHORT).show()
            }
            else if (hwArr[addButtonClickCounter - 1]?.toInt()!! > 100 || hwArr[addButtonClickCounter - 1]?.toInt()!! < 0) {
                hwArr = arrayOfNulls<String>(5)
                addButtonClickCounter = 0
                homework.isEnabled = true
                add.visibility = View.VISIBLE
                hwView.text = ""
                hwView.visibility = View.INVISIBLE
                output.visibility = View.INVISIBLE
                Toast.makeText(this, "please enter valid inputs for homeworks!", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun gradeCalculate(hw: Int, participationAndAttendanceInput: String, groupPresentationInput: String, midterm1Input: String, midterm2Input: String, finalProjectInput: String): String {
        val finalGrade = ((hw * 20) / 100) +
                ((participationAndAttendanceInput.toInt() * 10) / 100) +
                ((groupPresentationInput.toInt() * 10) / 100) +
                ((midterm1Input.toInt() * 10) / 100) +
                ((midterm2Input.toInt() * 20) / 100) +
                ((finalProjectInput.toInt() * 30) / 100)

        return finalGrade.toString()
    }

}