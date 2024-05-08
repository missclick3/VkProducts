package com.example.vkproducts.domain.usecases

import com.example.vkproducts.domain.repositories.ProductsRepository
import com.example.vkproducts.presentation.ProductsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PageChangedUseCase(
    private val repository: ProductsRepository
) {
    suspend fun execute(page: Int, query: String) : ProductsResult {
        return try {
            val response = withContext(Dispatchers.IO) {
                if (query.isBlank()) {
                    repository.getProducts(page)
                }
                else {
                    repository.searchProducts(query, page)
                }
            }
            val pages = repository.getAmountOfPages(query)
            val list = ArrayList<Int>()
            for (i in 0 until pages) {
                list.add(i+1)
            }
            ProductsResult.ProductsList(items = response, page = page, list)
        } catch (e: Exception) {
            ProductsResult.Error(e.message ?: "Unknown Error")
        }
    }
}