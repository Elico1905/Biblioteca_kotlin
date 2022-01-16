package com.elico.biblioteca.Alumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elico.biblioteca.Alumnos.Adapters.AdapterListBook
import com.elico.biblioteca.Alumnos.Adapters.AdapterListBookComments
import com.elico.biblioteca.Alumnos.Models.ModelListBook
import com.elico.biblioteca.Alumnos.Models.ModelListBookComments
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_book.*


class ActivityListBook : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterListBook
    var lista = mutableListOf<ModelListBook>();

    private lateinit var adapterComments: AdapterListBookComments
    var listaComments = mutableListOf<ModelListBookComments>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        ListBook_btn_Exit.setOnClickListener {
            HideAbout()
        }

        ListBook_button_reserve.setOnClickListener {

        }


        adapter = AdapterListBook(this)

        listBook_recyclerview.layoutManager =LinearLayoutManager(this)
        listBook_recyclerview.adapter = adapter
//        for(i in 1..3){
//            lista.add(ModelListBook("Nombre $i","Editorial $i","Autor $i",i,i,"uno","uno"))
//        }

        bd.collection("books").endAt().get().addOnSuccessListener {
                for (documentos in it){
                    Log.d("Documentos","${documentos.data.get("id")}")
                    lista.add(ModelListBook(
                        documentos.data.get("name").toString(),
                        documentos.data.get("editorial").toString(),
                        documentos.data.get("author").toString(),
                        "${documentos.data.get("edition")}".toInt(),
                        "${documentos.data.get("pages")}".toInt(),
                        "null",
                        documentos.data.get("id").toString()))
                }
            adapter.setListData(lista)
            adapter.notifyDataSetChanged()
            lista = adapter.returnListData()
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


    fun ShowAbout(){
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

    fun GetComments(id:String){

        listaComments.clear()
        ListBook_calification.text = "0"
        ListBook_calification_star.rating = 0f
        ListBook_message_null_calification.visibility = View.GONE

        Log.d("Documento","${id}")

        adapterComments = AdapterListBookComments(this)

        ListBook_recyclerview_comments.layoutManager =LinearLayoutManager(this)
        ListBook_recyclerview_comments.adapter = adapterComments

        bd.collection("comments").whereEqualTo("book","${id}").get().addOnSuccessListener {
            var NumUser:Int = 0
            var points:Int = 0

            for (documentos in it){
                //Log.d("Documentos","${documentos}")
                NumUser++
                points += "${documentos.get("calification")}".toInt()
                listaComments.add(
                    ModelListBookComments(
                        documentos.get("book").toString(),
                        documentos.get("matricula").toString(),
                        "${documentos.get("calification")}".toInt(),
                        documentos.get("comment").toString()))
            }
                if (NumUser>0){
                    var resul:Float = points / NumUser.toFloat()
                    ListBook_calification.text = "${resul}"
                    ListBook_calification_star.rating = resul
                }else{
                    ListBook_message_null_calification.visibility = View.VISIBLE
                }
            adapterComments.setListData(listaComments)
            adapterComments.notifyDataSetChanged()
            listaComments = adapterComments.returnListData()
        }


    }


}