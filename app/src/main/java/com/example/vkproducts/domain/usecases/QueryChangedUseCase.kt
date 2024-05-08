package com.example.vkproducts.domain.usecases

import com.example.vkproducts.domain.repositories.ProductsRepository
import com.example.vkproducts.presentation.ProductsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QueryChangedUseCase (
    private val repository: ProductsRepository
) {
    suspend fun execute(query: String) : ProductsResult {
        return try {
            val response = withContext(Dispatchers.IO) {
                repository.searchProducts(query, 1)
            }
            val pages = repository.getAmountOfPages(query)
            val list = ArrayList<Int>()
            for (i in 0 until pages) {
                list.add(i+1)
            }
            ProductsResult.ProductsList(response, 1, list)
        } catch (e: Exception) {
            ProductsResult.Error(e.message ?: "Unknown error")
        }
    }
}