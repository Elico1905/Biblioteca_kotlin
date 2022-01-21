package com.elico.biblioteca.Alumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elico.biblioteca.R

class ActivityMessages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)



    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()
    }
}