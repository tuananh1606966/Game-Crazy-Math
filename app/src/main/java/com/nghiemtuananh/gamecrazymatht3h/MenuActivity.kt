package com.nghiemtuananh.gamecrazymatht3h

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity: AppCompatActivity() {
    val REQUEST_CODE_EDIT = 123
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        intent = Intent(this, MainActivity::class.java)
        iv_play.setOnClickListener {
//            startActivity(intent)
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            score = data.getIntExtra("score", 0)
            tv_last_score.setText(score.toString())
            tv_banduoc.isInvisible = false
            tv_last_score.isInvisible = false
            tv_diem.isInvisible = false
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}