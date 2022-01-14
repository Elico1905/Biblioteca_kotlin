package com.elico.biblioteca

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.elico.biblioteca.Alumnos.ActivityHome
import com.elico.biblioteca.Alumnos.ActivityListBook
import com.elico.biblioteca.Alumnos.ActivityRegister
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()
    private val GOOGLE_SING_IN = 100

    private var PhotoUser:String = ""
    private var LastNameUser:String = ""
    private var NameUser:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (ReadSharedpreferes()){
            startActivity(Intent(this, ActivityHome::class.java))
            finish()
        }



        login_button_singIn.setOnClickListener {
            if (login_user.text.isNullOrEmpty() || login_password.text.isNullOrEmpty()){
                HideKeyboard()
                ShowMessageError()
            }else{
                ShowProcessingData()
            }
        }
        login_register.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
            finish()
        }
        login_boton_google.setOnClickListener{ GoogleButton() }
        login_button_ok.setOnClickListener { HideMessageError() }
    }


    private fun HideKeyboard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun ShowMessageError(){
        login_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        login_fondo.visibility = View.VISIBLE
        login_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        login_message.visibility = View.VISIBLE
    }

    private fun HideMessageError(){
        login_fondo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
        login_fondo.visibility = View.GONE
        login_message.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down))
        login_message.visibility = View.GONE
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (login_fondo.visibility == View.VISIBLE){
            HideMessageError()
        }
    }

    private fun ShowProcessingData(){
        //HideProcessingData()
        bd.collection("users").whereEqualTo("matricula",login_user.text.toString()).get().addOnSuccessListener {
            var matricula:String  = ""
            var pass:String = ""
            var email:String = ""
            var name:String = ""
            var photo:String = ""
            for (documentos in it){
                matricula = documentos.data.get("matricula").toString()
                pass = documentos.data.get("pass").toString()
                email = documentos.data.get("email").toString()
                name = documentos.data.get("name").toString()
                photo = documentos.data.get("photo").toString()
            }
            if (matricula.isNotEmpty()){
                if (pass == login_password.text.toString()){
                    message("correcto")
                    SaveSharedpreferes(email,matricula,name,photo)
                    startActivity(Intent(this, ActivityHome::class.java))
                    finish()
                }else{
                    message("contraseña incorrecta ")
                }
            }else{
                message("Numero de control no encontrado")
            }
        }

    }

    private fun HideProcessingData(){

    }
    private fun GoogleButton(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            PhotoUser = account.photoUrl.toString()
                            LastNameUser = account.familyName.toString()
                            NameUser = account.givenName.toString()
                            ValidateUser(account.email?:"")
                        } else {
                            message("error: No hay cuenta seleccionada")
                        }
                    }

                }
            } catch (e: ApiException) {
            }

        }
    }

    private fun ValidateUser(email:String){
        bd.collection("users").whereEqualTo("email",email).get().addOnSuccessListener {
            var matricula:String  = ""
            var nombre:String = ""

            for (documentos in it){
                matricula = documentos.data.get("matricula").toString()
                nombre = documentos.data.get("name").toString()
            }
            if (matricula.isNotEmpty()){
                message("correcto")
                SaveSharedpreferes(email,matricula,nombre,PhotoUser)
                startActivity(Intent(this, ActivityHome::class.java))
                finish()
            }else{
                startActivity(Intent(this, ActivityRegister::class.java)
                    .putExtra("email",email)
                    .putExtra("last_name",LastNameUser)
                    .putExtra("name",NameUser)
                    .putExtra("photo",PhotoUser))
                finish()
                message("El correo no esta asociado a ninguna cuenta de gmail")
            }
        }
    }

    private fun SaveSharedpreferes(email:String,matricula:String,nombre:String,photo:String){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("nombre", nombre)
        prefs.putString("matricula", matricula)
        prefs.putString("photo", photo)
        prefs.apply()
    }
    
    private fun message(cadena:String){
        Toast.makeText(this, cadena, Toast.LENGTH_SHORT).show()
    }

    private fun ReadSharedpreferes():Boolean{
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var EMAIL_USER = prefs.getString("email", R.string.prefs_file_key.toString()).toString()
        var NAME_USER = prefs.getString("nombre", R.string.prefs_file_key.toString()).toString()
        var MATRICULA = prefs.getString("matricula", R.string.prefs_file_key.toString()).toString()
        var PHOTO_USER = prefs.getString("photo", R.string.prefs_file_key.toString()).toString()

        return EMAIL_USER != R.string.prefs_file_key.toString() || NAME_USER != R.string.prefs_file_key.toString() ||
                MATRICULA != R.string.prefs_file_key.toString() || PHOTO_USER != R.string.prefs_file_key.toString()
    }

}