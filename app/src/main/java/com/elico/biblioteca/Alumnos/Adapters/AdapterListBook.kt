package com.elico.biblioteca.Alumnos.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elico.biblioteca.Alumnos.ActivityListBook
import com.elico.biblioteca.Alumnos.Models.ModelListBook
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.activity_list_book.*
import kotlinx.android.synthetic.main.item_recyclerview_list_book.view.*

class AdapterListBook(private val context: Context):RecyclerView.Adapter<AdapterListBook.ViewHolderListBook>(){

    private var datalist = mutableListOf<ModelListBook>()

    fun setListData(data: MutableList<ModelListBook>) {
        datalist = data
    }

    fun returnListData(): MutableList<ModelListBook> {
        return datalist;
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AdapterListBook.ViewHolderListBook {
        val view: View =LayoutInflater.from(context).inflate(R.layout.item_recyclerview_list_book, parent, false)
        return ViewHolderListBook(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: AdapterListBook.ViewHolderListBook, position: Int) {
        val book = datalist[position]

        holder.itemView.item_list_book_card.setOnClickListener {
            if(context is ActivityListBook){
                context.GetComments(book.id)
                Glide.with(context).load(book.photo).into(context.ListBook_photo)
                context.ListBook_ScrollView.fullScroll(ScrollView.FOCUS_UP)
                context.ListBook_name.text = "Nombre: ${book.name}"
                context.ListBook_editorial.text = "Editorial: ${book.editorial}"
                context.ListBook_author.text = "Autor: ${book.author}"
                context.ListBook_edition.text = "Edicion: ${book.edition}"
                context.ListBook_pages.text = "Paginas: ${book.pages}"
                context.POSITION = position
                context.ShowAbout()
            }
        }
        holder.bindView(book)
    }

    inner class ViewHolderListBook(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(book:ModelListBook){
            itemView.item_list_book_name.text = book.name
            itemView.item_list_book_editorial.text = book.editorial
            Glide.with(itemView.item_list_book_photo.context).load(book.photo).into(itemView.item_list_book_photo)
        }
    }

}