package com.elico.biblioteca.Alumnos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elico.biblioteca.Alumnos.ActivityInProcess
import com.elico.biblioteca.Alumnos.ActivityListBook
import com.elico.biblioteca.Alumnos.Models.ModelProcess
import com.elico.biblioteca.R
import kotlinx.android.synthetic.main.item_recyclerview_process.view.*

class AdapterProcess (private val context: Context): RecyclerView.Adapter<AdapterProcess.ViewHolderProcess>(){

    private var datalist = mutableListOf<ModelProcess>()

    fun setListData(data: MutableList<ModelProcess>) {
        datalist = data
    }

    fun returnListData(): MutableList<ModelProcess> {
        return datalist;
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AdapterProcess.ViewHolderProcess {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_process, parent, false)
        return ViewHolderProcess(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: AdapterProcess.ViewHolderProcess, position: Int) {
        val process = datalist[position]
        holder.itemView.item_process_button.setOnClickListener {
            if(context is ActivityInProcess){
                Toast.makeText(context, "eliminar: ${process.id}", Toast.LENGTH_SHORT).show()
            }
        }
        holder.bindView(process)
    }


    inner class ViewHolderProcess(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindView(process: ModelProcess) {
            itemView.item_process_name.text = datalist[position].name
            Glide.with(itemView.item_process_photo.context).load(datalist[position].photo).into(itemView.item_process_photo)
        }
    }
}