package com.elico.biblioteca

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var cadena:String = ""
    private var estado:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)

        ojo.setOnClickListener {
            if (estado == true){
                cadena = pass.text.toString()
                var tam:Int = pass.text.length
                var aux:String = ""
                while (tam > 0){
                    aux += "• "
                    tam--
                }
                pass.setText("${aux}")
            }else{
                 pass.setText("${cadena}")
            }
            estado = !estado
        }
    }
}