package com.example.vkproducts.presentation

import com.example.vkproducts.data.messages.ProductDto

data class ListProductsState(
    val query: String = "",
    val page: Int = 1
)
