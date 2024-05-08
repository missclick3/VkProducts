package com.example.vkproducts.domain.repositories

import com.example.vkproducts.data.messages.ProductDto

interface ProductsRepository {
    suspend fun getProducts(page: Int) : List<ProductDto>
    suspend fun getProductById(id: Int) : ProductDto?
    suspend fun searchProducts(query: String, page: Int) : List<ProductDto>
    suspend fun getAmountOfPages(query: String = "") : Int
}