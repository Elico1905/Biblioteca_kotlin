package com.elico.biblioteca.Tools

class ToolsBook {

    fun ConverToLetters(num:Int):String{
       when(num){
           1 -> {return "Primera Edición"}
           2 -> {return "Segunda Edición"}
           3 -> {return "Tercera Edición"}
           4 -> {return "Cuarta Edición"}
           5 -> {return "Quinta Edición"}
           6 -> {return "Sexta Edición"}
           7 -> {return "Séptima Edición"}
           8 -> {return "Octava Edición"}
           9 -> {return "Novena Edición"}
           10 -> {return "Décima Edición"}
           else -> {return "${num} Edición"}
       }
    }
}