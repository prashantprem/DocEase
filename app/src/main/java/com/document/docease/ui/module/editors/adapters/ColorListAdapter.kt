package com.document.docease.ui.module.editors.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.document.docease.databinding.ItemColorRvBinding

class ColorListAdapter(
    private val colorList: ArrayList<String>,
    val context: Context,
    private val colorItemClickListener: ColorItemClickListener
) : RecyclerView.Adapter<ColorListAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemColorRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.colorBtn.setBackgroundColor(Color.parseColor(colorList[position]))
        holder.colorBtn.setOnClickListener {
            colorItemClickListener.onColorClicked(
                colorList[holder.adapterPosition]
            )
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    inner class RecyclerViewHolder(itemView: ItemColorRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val colorBtn: Button
        init {
            colorBtn = itemView.colorBtn
        }
    }

    interface ColorItemClickListener {
        fun onColorClicked(colorCode: String?)
    }
}
