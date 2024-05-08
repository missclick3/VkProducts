package com.example.vkproducts.presentation.recyclerPages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.vkproducts.R

class PagesAdapter(
    private val clickListener: OnRecyclerItemClicked
)
    : RecyclerView.Adapter<PagesAdapter.PagesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagesViewHolder {
        return PagesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vh_page_number, parent, false)
        )
    }

    private val numbersList:MutableList<Int> = mutableListOf()


    fun setPages(submittedNumbers: List<Int>) {
        numbersList.clear()
        numbersList.addAll(submittedNumbers)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PagesViewHolder, position: Int) {
        holder.bind(numbersList[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(numbersList[position], holder.itemView)
            holder.itemView.setBackgroundColor(android.graphics.Color.BLUE)
        }
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }

    inner class PagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPageNumber: TextView = itemView.findViewById(R.id.tvPageNumber)

        fun bind(data: Int) {
            tvPageNumber.text = data.toString()
        }
    }

    interface OnRecyclerItemClicked {
        fun onClick(pageNumber: Int, view: View)
    }
}