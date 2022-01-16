package com.elico.biblioteca.Alumnos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elico.biblioteca.Alumnos.Models.ModelListBookComments
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.item_recyclerview_list_book_comments.view.*

class AdapterListBookComments (private val context: Context): RecyclerView.Adapter<AdapterListBookComments.ViewHolderListBookComments>() {

    private var datalist = mutableListOf<ModelListBookComments>()

    fun setListData(data: MutableList<ModelListBookComments>) {
        datalist = data
    }

    fun returnListData(): MutableList<ModelListBookComments> {
        return datalist;
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AdapterListBookComments.ViewHolderListBookComments {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_list_book_comments, parent, false)
        return ViewHolderListBookComments(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: AdapterListBookComments.ViewHolderListBookComments,position: Int) {
        val comment = datalist[position]
        holder.bindView(comment)
    }


    inner class ViewHolderListBookComments(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(comment: ModelListBookComments){
            itemView.item_list_book_comment_name.text = comment.name
            itemView.item_list_book_comment_comment.text = comment.comment
            itemView.item_list_book_comment_star.rating = comment.calification.toFloat()
        }
    }
}