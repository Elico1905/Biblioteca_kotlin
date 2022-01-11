package com.elico.biblioteca.Alumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.activity_list_book.*


class ActivityListBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        ListBook_btn_Exit.setOnClickListener {
            HideAbout()
        }
        test.setOnClickListener {
            ShowAbout()
        }

    }

    override fun onBackPressed() {
        if (ListBook_fondo.visibility == View.VISIBLE){
            HideAbout()
        }else{
            if (ListBook_fondo.visibility == View.GONE){
                super.onBackPressed()
            }
        }

    }


    private fun ShowAbout(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        ListBook_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        ListBook_fondo.visibility = View.VISIBLE
        ListBook_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        ListBook_About.visibility = View.VISIBLE
    }


    private fun HideAbout(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)

        ListBook_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        ListBook_fondo.visibility = View.GONE
        ListBook_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        ListBook_About.visibility = View.GONE
    }



}