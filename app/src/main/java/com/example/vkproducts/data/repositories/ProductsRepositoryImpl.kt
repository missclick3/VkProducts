package com.example.vkproducts.data.repositories

import com.example.vkproducts.data.messages.ProductDto
import com.example.vkproducts.data.remote.api.ProductsApi
import com.example.vkproducts.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl (
    private val api: ProductsApi
) : ProductsRepository {
    override suspend fun getProducts(page: Int): List<ProductDto> {
        return api.getProducts(
            skip = 20*(page-1),
            limit = 20
        ).products
    }

    override suspend fun getProductById(id: Int): ProductDto? {
        return api.getProductById(id)
    }

    override suspend fun searchProducts(query: String, page: Int): List<ProductDto> {
        return api.searchProducts(
            query = query,
            skip = 20 * (page - 1),
            limit = 20
        ).products
    }

    override suspend fun getAmountOfPages(query: String): Int {
        return if (query.isBlank()) {
            val response = api.getProducts(skip = 0, limit = 0).products.size
            totalToPages(response)
        } else {
            val response = api.searchProducts(query, skip = 0, limit = 0).products.size
            totalToPages(response)
        }
    }

    private fun totalToPages(total: Int) : Int {
        return if (total % 20 == 0) {
            total / 20
        } else {
            (total + 20) / 20
        }
    }
}