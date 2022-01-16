package com.elico.biblioteca.Alumnos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.elico.biblioteca.Login
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class ActivityRegister : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()
    private var PhotoUser:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val parametros = this.intent.extras
        if (parametros != null){
            register_email.setText(parametros.getString("email"))
            register_name.setText(parametros.getString("name"))
            register_last_name_1.setText(parametros.getString("last_name"))
            register_email.isEnabled = false
            PhotoUser = parametros.getString("photo").toString()
        }

        register_button_cancel.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        register_button_register.setOnClickListener {
            ValidateMatricula(register_matricula.text.toString())
            //prueba()
        }

    }

    private fun RegisterStudent() {
        bd.collection("users").document(register_matricula.text.toString()).set(
            hashMapOf(
                "email" to register_email.text.toString(),
                "matricula" to register_matricula.text.toString(),
                "pass" to register_pass.text.toString(),
                "name" to register_name.text.toString(),
                "last_name_1" to register_last_name_1.text.toString(),
                "last_name_2" to register_last_name_2.text.toString(),
                "photo" to if(PhotoUser == ""){"NoPhoto"}else{PhotoUser}))
        Toast.makeText(this, "alumnos registrado", Toast.LENGTH_SHORT).show()
        SaveSharedpreferes(register_email.text.toString(),register_matricula.text.toString(),register_name.text.toString())
    }


    private fun SaveSharedpreferes(email:String,matricula:String,nombre:String){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("nombre", nombre)
        prefs.putString("matricula", matricula)
        prefs.putString("photo", if(PhotoUser == ""){"NoPhoto"}else{PhotoUser})
        prefs.apply()
        startActivity(Intent(this, ActivityHome::class.java))
        finish()
    }

    private fun ValidateMatricula(matricula: String){
        bd.collection("users").whereEqualTo("matricula",matricula).get().addOnSuccessListener {
            var res = ""
            for (documentos in it){
                res = documentos.data.get("matricula").toString()
            }
            if (res.isNotEmpty()){
                Toast.makeText(this, "La matricula ya se esta usando", Toast.LENGTH_SHORT).show()
            }else{
                ValidateEmail()
            }
        }
    }


    private fun ValidateEmail(){
        bd.collection("users").whereEqualTo("email",register_email.text.toString()).get().addOnSuccessListener {
            var res = ""
            for (documentos in it){
                res = documentos.data.get("email").toString()
            }
            if (res.isNotEmpty()){
                Toast.makeText(this, "El email ya se esta usando", Toast.LENGTH_SHORT).show()
            }else{
                RegisterStudent()
            }
        }
    }



    private fun prueba(){

        bd.collection("books").document().set(
            hashMapOf(
                "name" to "programacion orientada a objetos",
                "editorial" to "alba",
                "author" to "Jaime Lopez Becerril",
                "edition" to "1",
                "pages" to "462",
                "photo" to "",
                "id" to ""))

        bd.collection("books")
            .whereEqualTo("name","programacion orientada a objetos")
            .whereEqualTo("editorial","alba")
            .whereEqualTo("author","Jaime Lopez Becerril")
            .whereEqualTo("edition","1")
            .whereEqualTo("pages","462").get().addOnSuccessListener {
                for (documentos in it){
                    Log.d("Documentos","${documentos.id}<-->${documentos.data}")
                    Log.d("Documentos","el id en el for es :${documentos.id}")
                    preubaTest(documentos.id)
                    break
            }
                Log.d("Documento","terminado")
        }
    }
    private fun preubaTest(id:String){
        try {
            bd.collection("books").document(id).set(
                hashMapOf(
                    "name" to "programacion orientada a objetos 3",
                    "editorial" to "alba",
                    "author" to "Jaime Lopez Becerril",
                    "edition" to "1",
                    "pages" to "462",
                    "photo" to "si hay photo",
                    "id" to "${id}"))
        }catch (e:Exception){
            Log.d("Documento","error: ${e}")
        }
    }


    override fun onBackPressed() {
        //super.onBackPressed()
    }
}