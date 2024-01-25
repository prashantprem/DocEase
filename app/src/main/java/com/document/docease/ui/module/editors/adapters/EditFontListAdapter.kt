package com.document.docease.ui.module.editors.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.document.docease.databinding.ItemFontFamilyRvBinding

class EditFontListAdapter(
    private var mDataList: ArrayList<String>,
    val mContext: Context,
    private val fontItemClickListener: FontItemClickListener
) : RecyclerView.Adapter<EditFontListAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemFontFamilyRvBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemTv.text = mDataList[position]
        holder.itemTv.setOnClickListener { fontItemClickListener.onFontItemClicked(mDataList[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun updateData(list:ArrayList<String>){
        mDataList.clear()
        mDataList = list
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(itemView: ItemFontFamilyRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        val itemTv: TextView
        init {
            itemTv = itemView.textFieldRv
        }
    }

    interface FontItemClickListener {
        fun onFontItemClicked(fontItem: String?)
    }
}
