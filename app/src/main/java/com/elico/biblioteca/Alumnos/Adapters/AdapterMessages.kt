package com.elico.biblioteca.Alumnos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elico.biblioteca.Alumnos.ActivityMessages
import com.elico.biblioteca.Alumnos.Models.ModelMessages
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.item_recyclerview_messages.view.*

class AdapterMessages(private val contex:Context): RecyclerView.Adapter<AdapterMessages.ViewHolderMessages>() {

    private var datalist = mutableListOf<ModelMessages>()

    fun setListData(data: MutableList<ModelMessages>) {
        datalist = data
    }

    fun returnListData(): MutableList<ModelMessages> {
        return datalist;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMessages.ViewHolderMessages {
        val view: View = LayoutInflater.from(contex).inflate(R.layout.item_recyclerview_messages, parent, false)
        return ViewHolderMessages(view)
    }


    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: AdapterMessages.ViewHolderMessages, position: Int) {
        val messages = datalist[position]
        holder.itemView.item_messages_card.setOnClickListener {
            if (contex is ActivityMessages){
                contex.ShowMessage(position)
            }
        }

        holder.bindView(messages)
    }

    inner class ViewHolderMessages(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(messages:ModelMessages){
            itemView.item_messages_date.text = messages.date_answer
            itemView.item_messages_message.text = messages.message
        }
    }

}