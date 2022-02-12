package com.nghiemtuananh.gamecrazymatht3h

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val random = Random
    private var numberOne: Int
    private var numberTwo: Int
    private var numberResult: Int
    private var addNumberWrong: Int
    private var numberResultWrong: Int
    private var score = 0
    private lateinit var th: Thread
    private var time = 10

    init {
        numberOne = random.nextInt(100)
        numberTwo = random.nextInt(100)
        numberResult = numberOne + numberTwo
        addNumberWrong = random.nextInt(5)
        numberResultWrong = numberResult + addNumberWrong
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent = Intent()
        starGame()
        iv_true.setOnClickListener(this)
        iv_false.setOnClickListener(this)
        timeCountDown()
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            tv_time.setText(msg.obj.toString())
            if (tv_time.text == "0") {
                Toast.makeText(this@MainActivity,
                    "Đã hết thời gian, Game Over!",
                    Toast.LENGTH_LONG).show()
                intent.putExtra("score", score)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onClick(v: View) {
        when (v) {
            iv_true -> {
                if (tv_result.text != numberResult.toString()) {
                    Toast.makeText(this, "Bạn đã trả lời sai! Game Over!", Toast.LENGTH_LONG).show()
                    intent.putExtra("score", score)
                    setResult(RESULT_OK, intent)
                    time = 11
                    finish()
                } else {
                    Toast.makeText(this, "Bạn đã trả lời đúng!", Toast.LENGTH_LONG).show()
                    score++
                    tv_score.setText(score.toString())
                    time = 10
                    createGame()
                    starGame()
                }
            }
            iv_false -> {
                if (tv_result.text == numberResult.toString()) {
                    Toast.makeText(this, "Bạn đã trả lời sai! Game Over!", Toast.LENGTH_LONG).show()
                    intent.putExtra("score", score)
                    setResult(RESULT_OK, intent)
                    time = 11
                    finish()
                } else {
                    Toast.makeText(this, "Bạn đã trả lời đúng!", Toast.LENGTH_LONG).show()
                    score++
                    tv_score.setText(score.toString())
                    time = 10
                    createGame()
                    starGame()
                }
            }
        }
    }

    private fun createGame() {
        numberOne = random.nextInt(100)
        numberTwo = random.nextInt(100)
        numberResult = numberOne + numberTwo
        addNumberWrong = random.nextInt(5)
        numberResultWrong = numberResult + addNumberWrong
    }

    private fun starGame() {
        tv_numberOne.setText(numberOne.toString())
        tv_numberTwo.setText(numberTwo.toString())
        if (numberOne % 2 == 0) {
            tv_result.setText(numberResult.toString())
        } else {
            tv_result.setText(numberResultWrong.toString())
        }
    }

    private fun timeCountDown() {
        val runnable = object : Runnable {
            override fun run() {
                while (time < 11) {
                    val message = Message()
                    message.obj = time
                    message.target = mHandler
                    message.sendToTarget()
                    time--
                    SystemClock.sleep(1000)
                }
            }
        }
        th = Thread(runnable)
        th.start()
    }
}