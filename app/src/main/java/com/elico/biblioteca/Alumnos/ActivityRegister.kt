package com.elico.biblioteca.Alumnos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elico.biblioteca.Login
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

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
            RegisterStudent()
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

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}