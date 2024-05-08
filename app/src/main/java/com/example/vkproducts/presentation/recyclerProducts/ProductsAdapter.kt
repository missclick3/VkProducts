package com.example.vkproducts.presentation.recyclerProducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vkproducts.R
import com.example.vkproducts.data.messages.ProductDto

class ProductsAdapter()
    : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vh_product, parent, false)
        )
    }

    private val productsList:MutableList<ProductDto> = mutableListOf()

    fun setProducts(products: List<ProductDto>) {
        productsList.clear()
        productsList.addAll(products)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        fun bind(data: ProductDto) {
            tvTitle.text = data.title
            tvDescription.text = data.description
            tvPrice.text = "${data.price}$"
            Glide.with(ivThumbnail).load(data.thumbnail).into(ivThumbnail)
        }
    }
}