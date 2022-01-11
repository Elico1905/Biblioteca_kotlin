package com.elico.biblioteca

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.elico.biblioteca.Alumnos.ActivityHome
import com.elico.biblioteca.Alumnos.ActivityRegister
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_singIn.setOnClickListener {
            if (login_user.text.isNullOrEmpty() || login_password.text.isNullOrEmpty()){
                HideKeyboard()
                ShowMessageError()
            }else{
                ShowProcessingData()
            }
        }
        login_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
            finish()
        }
        login_boton_google.setOnClickListener{ GoogleButton() }
        login_button_ok.setOnClickListener { HideMessageError() }
    }


    private fun HideKeyboard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun ShowMessageError(){
        login_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        login_fondo.visibility = View.VISIBLE
        login_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        login_message.visibility = View.VISIBLE
    }

    private fun HideMessageError(){
        login_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        login_fondo.visibility = View.GONE
        login_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        login_message.visibility = View.GONE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (login_fondo.visibility == View.VISIBLE){
            HideMessageError()
        }
    }

    private fun ShowProcessingData(){
        startActivity(Intent(this, ActivityHome::class.java))
        finish()
        HideProcessingData()
    }

    private fun HideProcessingData(){

    }
    private fun GoogleButton(){

    }



}