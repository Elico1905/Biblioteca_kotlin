package com.elico.biblioteca.Alumnos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.elico.biblioteca.Login
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    private var PHOTO_USER:String = ""
    private var MATRICULA:String = ""
    private var NAME_USER:String = ""
    private var EMAIL_USER:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (ReadSharedpreferes()){
            home_name_2.text = "Nombre: ${NAME_USER}"
            home_name.text = "Nombre: ${NAME_USER}"
            home_matricula_2.text = "Control: ${MATRICULA}"
            home_matricula.text = "Control: ${MATRICULA}"
            if (PHOTO_USER != "NoPhoto"){
                Glide.with(this).load(PHOTO_USER).into(home_photo_2)
                Glide.with(this).load(PHOTO_USER).into(home_photo)
            }
        }else{
            Toast.makeText(this, "ir a login", Toast.LENGTH_SHORT).show()
        }


        home_ListBook.setOnClickListener {
            startActivity(Intent(this, ActivityListBook::class.java))
        }
        home_button_exit.setOnClickListener {
            ShowMessageExit()
        }
        home_button_cancel.setOnClickListener {
            HideMessageExit()
        }
        home_button_ok.setOnClickListener {
            DeleteSharedPreferences()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        home_btn_about_exit.setOnClickListener {
            HideBtnAboutExit()
        }
        home_section_profile.setOnClickListener {
            ShowBtnAboutExit()
        }
    }


    private fun ShowMessageExit(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        home_fondo.visibility = View.VISIBLE
        home_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        home_message.visibility = View.VISIBLE
    }

    private fun HideMessageExit(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        home_fondo.visibility = View.GONE
        home_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        home_message.visibility = View.GONE
    }


    private fun HideBtnAboutExit(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        home_fondo.visibility = View.GONE
        home_about.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        home_about.visibility = View.GONE
    }

    private fun ShowBtnAboutExit(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        home_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        home_fondo.visibility = View.VISIBLE
        home_about.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        home_about.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (home_fondo.visibility == View.VISIBLE){
            when(View.VISIBLE){
                home_message.visibility -> { HideMessageExit() }
                home_about.visibility -> { HideBtnAboutExit() }
            }
        }
    }

    private fun ReadSharedpreferes():Boolean{
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        EMAIL_USER = prefs.getString("email", R.string.prefs_file_key.toString()).toString()
        NAME_USER = prefs.getString("nombre", R.string.prefs_file_key.toString()).toString()
        MATRICULA = prefs.getString("matricula", R.string.prefs_file_key.toString()).toString()
        PHOTO_USER = prefs.getString("photo", R.string.prefs_file_key.toString()).toString()

        return EMAIL_USER != R.string.prefs_file_key.toString() || NAME_USER != R.string.prefs_file_key.toString() ||
                MATRICULA != R.string.prefs_file_key.toString() || PHOTO_USER != R.string.prefs_file_key.toString()
    }

    private fun DeleteSharedPreferences() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
    }
}