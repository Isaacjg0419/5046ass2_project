package com.example.a5046ass2_project.WishList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a5046ass2_project.Map.Property
import com.example.a5046ass2_project.R



class PropertyAdapter(private val properties: MutableList<Property>) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_card_view, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    deleteProperty(position)
                }
            }
        }

        fun bind(property: Property) {
            addressTextView.text = property.address
            priceTextView.text = property.price.toString()
        }
    }

    fun deleteProperty(position: Int) {
        properties.removeAt(position)
        notifyItemRemoved(position)
    }
}


