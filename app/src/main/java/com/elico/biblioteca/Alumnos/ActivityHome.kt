package com.elico.biblioteca.Alumnos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.elico.biblioteca.Login
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        home_ListBook.setOnClickListener {
            startActivity(Intent(this, ActivityListBook::class.java))

        }
        home_button_exit.setOnClickListener {
            ShowMessageError()
        }
        home_button_cancel.setOnClickListener {
            HideMessageError()
        }
        home_button_ok.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }


    private fun ShowMessageError(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        home_fondo.visibility = View.VISIBLE
        home_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        home_message.visibility = View.VISIBLE
    }

    private fun HideMessageError(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        home_fondo.visibility = View.GONE
        home_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        home_message.visibility = View.GONE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (home_fondo.visibility == View.VISIBLE){
            HideMessageError()
        }
    }
}