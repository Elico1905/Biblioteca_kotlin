package com.elico.biblioteca.Alumnos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elico.biblioteca.Alumnos.Adapters.AdapterProcess
import com.elico.biblioteca.Alumnos.Models.ModelProcess
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_in_process.*
import kotlinx.android.synthetic.main.activity_list_book.*

class ActivityInProcess : AppCompatActivity() {

    var lista = mutableListOf<ModelProcess>()
    private val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterProcess
    var MATRICULA:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_process)


        val bundle = intent.extras
        MATRICULA = bundle?.getString("matricula").toString()
        if (MATRICULA == ""){
            Toast.makeText(this, "error de alumno", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ActivityHome::class.java))
        }
        process_button_option_ok.setOnClickListener {

        }
        process_button_option_cancel.setOnClickListener {
            HideMessageCancel()
        }
        process_button_back.setOnClickListener {
            finish()
        }

        adapter = AdapterProcess(this)
        process_recyclerview.layoutManager = LinearLayoutManager(this)
        process_recyclerview.adapter = adapter
        bd.collection("trajectory")
            .whereEqualTo("matricula","${MATRICULA}")
            .whereEqualTo("process",true)
            .get().addOnSuccessListener {
                for (documentos in it){
                    lista.add(ModelProcess(documentos.data.get("book_name").toString(),
                        documentos.data.get("book_photo").toString(),
                        documentos.data.get("book_id").toString()))
                }
                if (lista.size > 0){
                    process_message.visibility = View.GONE
                    adapter.setListData(lista)
                    adapter.notifyDataSetChanged()
                    lista = adapter.returnListData()
                }else{
                    Log.d("Documento","no hay libros en proceso aun")
                    process_recyclerview.visibility = View.GONE
                }
            }
    }



    fun ShowMessageCancel(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.green_shadow)

        process_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        process_fondo.visibility = View.VISIBLE
        process_message_confirmation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        process_message_confirmation.visibility = View.VISIBLE
    }

    private fun HideMessageCancel(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.green)

        process_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        process_fondo.visibility = View.GONE
        process_message_confirmation.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        process_message_confirmation.visibility = View.GONE
    }




}