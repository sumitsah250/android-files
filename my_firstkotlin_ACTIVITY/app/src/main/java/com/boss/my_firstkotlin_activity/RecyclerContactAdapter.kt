package com.boss.my_firstkotlin_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class RecyclerContactAdapter(val context : Context,val arrContacts : ArrayList<ContactModel>) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id : TextView = itemView.findViewById(R.id.id)

        val name : TextView = itemView.findViewById(R.id.name)
        val number: TextView = itemView.findViewById(R.id.number)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerContactAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row,parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerContactAdapter.ViewHolder, position: Int) {
        holder.id.text = arrContacts[position].id.toString()
        holder.name.text = arrContacts[position].name.toString()
        holder.number.text = arrContacts[position].number.toString()

    }

    override fun getItemCount(): Int {
        return arrContacts.size
    }
}