package com.elico.biblioteca.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elico.biblioteca.Alumnos.Models.ModelListBookComments
import com.elico.biblioteca.R
import com.google.firebase.firestore.FirebaseFirestore

class ActivityTest : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()

    private var list = mutableListOf<Books>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        list.add(Books("Cracking The Coding Interview","Gayle Laakmann Mcdowell","Careercup","708","2","","https://firebasestorage.googleapis.com/v0/b/biblioteca-ebc23.appspot.com/o/books%2Fimagen1.jpg?alt=media&token=a91b0398-8074-49ed-a4c0-c66a842a1c09"))
        list.add(Books("Expert C Programming: Deep C Secrets","Peter Van Der Linden","Pearson","384","6","","https://firebasestorage.googleapis.com/v0/b/biblioteca-ebc23.appspot.com/o/books%2Fimagen2.jpg?alt=media&token=988017d4-c2d9-41b1-8d29-99be325fd9b6"))
        list.add(Books("Inteligencia Artificial","Lasse Rouhiainen","Alienta Editorial","176","1","","https://firebasestorage.googleapis.com/v0/b/biblioteca-ebc23.appspot.com/o/books%2Fimagen5.jpg?alt=media&token=c4ef1abf-22e4-4aec-b913-c99d95b578f3"))
        list.add(Books("Enigmas De La Humanidad","Pedro Silva","Ediciones Corona Borealis","308","5","","https://firebasestorage.googleapis.com/v0/b/biblioteca-ebc23.appspot.com/o/books%2Fimagen6.jpg?alt=media&token=4dba084d-14fc-4309-b293-50baf95aa266"))
        list.add(Books("The Culture Code","Daniel Coyle","Bantam","304","5","","https://firebasestorage.googleapis.com/v0/b/biblioteca-ebc23.appspot.com/o/books%2Fimagen8.jpg?alt=media&token=2f43ec7c-e2f0-44e2-9c0e-1da8dc67106d"))



        for(i in 0..4){
//
//        bd.collection("books").document().set(
//            hashMapOf(
//                "name" to "${list.get(i).name}",
//                "editorial" to "${list.get(i).editorial}",
//                "author" to "${list.get(i).autor}",
//                "edition" to "${list.get(i).edicion}",
//                "pages" to "${list.get(i).paginas}",
//                "photo" to "${list.get(i).photo}",
//                "id" to ""))


        }



//        bd.collection("books").document().set(
//            hashMapOf(
//                "name" to "programacion orientada a objetos",
//                "editorial" to "alba",
//                "author" to "Jaime Lopez Becerril",
//                "edition" to "1",
//                "pages" to "462",
//                "photo" to "",
//                "id" to ""))

    }
}

data class Books(
    var name:String,
    var autor:String,
    var editorial:String,
    var paginas:String,
    var edicion:String,
    var id:String,
    var photo:String
)
