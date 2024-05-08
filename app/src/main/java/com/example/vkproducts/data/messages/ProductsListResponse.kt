package com.example.vkproducts.data.messages

import kotlinx.serialization.Serializable

@Serializable
data class ProductsListResponse(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
