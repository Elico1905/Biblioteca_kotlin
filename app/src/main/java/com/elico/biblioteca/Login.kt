package com.elico.biblioteca

import android.content.Intent
import android.content.pm.ActivityInfo
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)

        login_button_singIn.setOnClickListener {
            if (login_user.text.isNullOrEmpty() || login_password.text.isNullOrEmpty()){
                HideKeyboard()
                ShowMessageError("message")
            }else{
                ShowProcessingData()
            }
        }
        login_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
        login_boton_google.setOnClickListener{ GoogleButton() }
        login_button_ok.setOnClickListener { HIdeMessageError() }
    }


    private fun HideKeyboard(){}

    private fun ShowMessageError(message:String){
        login_fondo.visibility = View.VISIBLE
    }

    private fun HIdeMessageError(){
        login_fondo.visibility = View.GONE
    }

    private fun ShowProcessingData(){
        startActivity(Intent(this, ActivityHome::class.java))
        HideProcessingData()
    }

    private fun HideProcessingData(){

    }
    private fun GoogleButton(){

    }



}