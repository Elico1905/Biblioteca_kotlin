package com.elico.biblioteca.Alumnos

import android.content.Intent
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
    var lista = mutableListOf<ModelListBook>()

    var POSITION:Int = -1
    var MATRICULA:String = ""

    private lateinit var adapterComments: AdapterListBookComments
    var listaComments = mutableListOf<ModelListBookComments>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        val bundle = intent.extras
        MATRICULA = bundle?.getString("matricula").toString()
        if (MATRICULA == ""){
            Toast.makeText(this, "error de alumno", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ActivityHome::class.java))
        }

        ListBook_btn_Exit.setOnClickListener {
            HideAbout()
        }

        ListBook_button_reserve.setOnClickListener {
           VerifyReserver()
        }

        ListBook_button_back.setOnClickListener {
            finish()
        }

        adapter = AdapterListBook(this)

        listBook_recyclerview.layoutManager =LinearLayoutManager(this)
        listBook_recyclerview.adapter = adapter
//        for(i in 1..3){
//            lista.add(ModelListBook("Nombre $i","Editorial $i","Autor $i",i,i,"uno","uno"))
//        }

        bd.collection("books").get().addOnSuccessListener {
                for (documentos in it){
                    //Log.d("Documentos","${documentos.data.get("id")}")
                    lista.add(ModelListBook(
                        documentos.data.get("name").toString(),
                        documentos.data.get("editorial").toString(),
                        documentos.data.get("author").toString(),
                        "${documentos.data.get("edition")}".toInt(),
                        "${documentos.data.get("pages")}".toInt(),
                        documentos.data.get("photo").toString(),
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
                //super.onBackPressed()
                  finish()
            }
        }

    }


    fun ShowAbout(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.green_shadow)

        ListBook_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        ListBook_fondo.visibility = View.VISIBLE

        ListBook_ScrollView.fullScroll(ScrollView.FOCUS_UP)

        ListBook_About.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        ListBook_About.visibility = View.VISIBLE
    }


    private fun HideAbout(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.green)

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

        //Log.d("Documento","${id}")

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
                    Listbook_hide.visibility = View.VISIBLE
                    var resul:Float = points / NumUser.toFloat()
                    ListBook_calification.text = "${resul}"
                    ListBook_calification_star.rating = resul
                }else{
                    ListBook_message_null_calification.visibility = View.VISIBLE
                    Listbook_hide.visibility = View.GONE
                }
            adapterComments.setListData(listaComments)
            adapterComments.notifyDataSetChanged()
            listaComments = adapterComments.returnListData()
        }
    }

    private fun VerifyReserver(){
        bd.collection("trajectory")
            .whereEqualTo("book_id","${lista.get(POSITION).id}")
            .whereEqualTo("matricula","${MATRICULA}")
            .whereEqualTo("book_name","${lista.get(POSITION).name}")
            .whereEqualTo("process",true)
            .get().addOnSuccessListener {
                var id:String = ""
                for (documentos in it){
                    id = documentos.data.get("book_id").toString()
                    Log.d("Documentos",documentos.data.toString())
                }
                if (id.isNotEmpty()){
                    Toast.makeText(this, "ya lo reservaste", Toast.LENGTH_SHORT).show()
                }else{
                    ReserverBook()
                }
        }
    }

    private fun ReserverBook(){
        bd.collection("trajectory").document().set(
            hashMapOf(
                "book_id" to "${lista.get(POSITION).id}",
                "book_name" to "${lista.get(POSITION).name}",
                "book_photo" to "${lista.get(POSITION).photo}",
                "matricula" to "${MATRICULA}",
                "menssage" to "",
                "answer" to false,
                "delivered" to false,
                "returned" to false,
                "cancel" to false,
                "date_answer" to "",
                "date_delivred" to "",
                "date_retured" to "",
                "process" to true,
                "alu_cancel" to false,
                "alu_date" to "hoy"
            ))

        Toast.makeText(this, "reservado", Toast.LENGTH_SHORT).show()
    }
}