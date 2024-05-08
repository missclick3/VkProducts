package com.example.vkproducts.data.remote.api

import com.example.vkproducts.data.messages.ProductDto
import com.example.vkproducts.data.messages.ProductsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int) : ProductDto?

    @GET("/products")
    suspend fun getProducts(@Query("skip") skip: Int, @Query("limit") limit: Int) : ProductsListResponse

    @GET("/products/search")
    suspend fun searchProducts(@Query("q") query: String, @Query("skip") skip: Int, @Query("limit") limit: Int) : ProductsListResponse

    @GET("products/categories")
    suspend fun getCategories() : List<String>

    @GET("/products/category/{category}")
    suspend fun getProductsForCategory(@Path("category") category: String, @Query("skip") skip: Int, @Query("limit") limit: Int) : ProductsListResponse
}