package com.elico.biblioteca.Alumnos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.elico.biblioteca.Alumnos.Adapters.AdapterMessages
import com.elico.biblioteca.Alumnos.Models.ModelMessages
import com.elico.biblioteca.R
import com.elico.biblioteca.Tools.ToolsBook
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_in_process.*
import kotlinx.android.synthetic.main.activity_messages.*
import kotlinx.android.synthetic.main.item_recyclerview_list_book.view.*

class ActivityMessages : AppCompatActivity() {

    var MATRICULA:String = ""
    var lista = mutableListOf<ModelMessages>()
    private val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterMessages

    private var ToolsBook:ToolsBook = ToolsBook()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        val bundle = intent.extras
        MATRICULA = bundle?.getString("matricula").toString()
        if (MATRICULA == ""){
            Toast.makeText(this, "error de alumno", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ActivityHome::class.java))
        }

        Messages_button_back.setOnClickListener {
            finish()
        }
        Messages_btn_Exit.setOnClickListener {
            HideMessage()
        }

        adapter = AdapterMessages(this)
        Messages_recycler.layoutManager = LinearLayoutManager(this)
        Messages_recycler.adapter = adapter

        bd.collection("trajectory")
            .whereEqualTo("matricula","${MATRICULA}")
            .whereEqualTo("process",false)
            .whereEqualTo("answer",true)
            .get().addOnSuccessListener {
                for (documentos in it){
                    lista.add(ModelMessages(documentos.data.get("book_id").toString(),
                        documentos.data.get("message").toString(),
                        documentos.data.get("alu_date").toString(),
                        documentos.data.get("date_answer").toString()))
                }



                if (lista.size > 0){
                    item_messages_null_message.visibility = View.GONE
                    adapter.setListData(lista)
                    adapter.notifyDataSetChanged()
                    lista = adapter.returnListData()
                }else{
                    Messages_recycler.visibility = View.GONE
                    item_messages_null_message.visibility = View.VISIBLE
                }

            }


        adapter.setListData(lista)
        adapter.notifyDataSetChanged()
        lista = adapter.returnListData()

    }




    fun ShowMessage(position:Int){
        Messages_date_reservation.text = lista[position].date_reservation
        Messages_message.text = lista[position].message
        Messages_date_answer.text = lista[position].date_answer

        GetBook(lista[position].id_book)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.blue_shadow)

        Messages_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        Messages_fondo.visibility = View.VISIBLE

        Messages_ScrollView.fullScroll(ScrollView.FOCUS_UP)

        Messages_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        Messages_About.visibility = View.VISIBLE
    }

    private fun GetBook(id:String){
        bd.collection("books").document(id).get().addOnSuccessListener {
            Glide.with(this).load(it.get("photo") as String?).into(Messages_photo)
            Messages_name.text = it.get("name") as String?
            Messages_editorial.text = it.get("editorial") as String?
            Messages_author.text = it.get("author") as String?
            Messages_edition.text = ToolsBook.ConverToLetters("${it.get("edition") as String}".toInt())
            Messages_pages.text = "${it.get("pages") as String?} paginas"
        }
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
        if (Messages_fondo.visibility == View.VISIBLE){
            if (Messages_About.visibility == View.VISIBLE){
                HideMessage()
            }
        }else{
            finish()
        }
    }
}