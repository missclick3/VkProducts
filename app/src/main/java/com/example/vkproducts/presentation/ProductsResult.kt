package com.example.vkproducts.presentation

import com.example.vkproducts.data.messages.ProductDto

sealed class ProductsResult {
    data object Loading: ProductsResult()
    data class Error(val errorText: String) : ProductsResult()
    data class ProductsList(val items: List<ProductDto>, val page: Int, val pages: List<Int>) : ProductsResult()
}