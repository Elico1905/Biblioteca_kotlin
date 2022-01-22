package com.elico.biblioteca.Alumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elico.biblioteca.Alumnos.Adapters.AdapterMessages
import com.elico.biblioteca.Alumnos.Adapters.AdapterProcess
import com.elico.biblioteca.Alumnos.Models.ModelMessages
import com.elico.biblioteca.Alumnos.Models.ModelProcess
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_in_process.*
import kotlinx.android.synthetic.main.activity_list_book.*
import kotlinx.android.synthetic.main.activity_messages.*

class ActivityMessages : AppCompatActivity() {

    var lista = mutableListOf<ModelMessages>()
    private val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterMessages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)


        Messages_button_back.setOnClickListener {
            finish()
        }
        Messages_btn_Exit.setOnClickListener {
            HideMessage()
        }

        adapter = AdapterMessages(this)
        Messages_recycler.layoutManager = LinearLayoutManager(this)
        Messages_recycler.adapter = adapter


        for (i in 1..10){
            lista.add(ModelMessages("${i}","","20/0${i}/2022"))
        }



        adapter.setListData(lista)
        adapter.notifyDataSetChanged()
        lista = adapter.returnListData()

    }




    fun ShowMessage(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        Messages_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        Messages_fondo.visibility = View.VISIBLE

        Messages_ScrollView.fullScroll(ScrollView.FOCUS_UP)

        Messages_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        Messages_About.visibility = View.VISIBLE
    }

    private fun HideMessage(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue)

        Messages_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        Messages_fondo.visibility = View.GONE
        Messages_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        Messages_About.visibility = View.GONE
    }


    override fun onBackPressed() {
        //super.onBackPressed()
        if (Messages_fondo.visibility == View.VISIBLE){
            if (Messages_About.visibility == View.VISIBLE){
                HideMessage()
            }
        }else{
            finish()
        }

    }
}