package com.example.vkproducts.di

import com.example.vkproducts.data.remote.api.ProductsApi
import com.example.vkproducts.data.repositories.ProductsRepositoryImpl
import com.example.vkproducts.domain.repositories.ProductsRepository
import com.example.vkproducts.domain.usecases.GetAllProductsRequest
import com.example.vkproducts.domain.usecases.ListProductsUseCases
import com.example.vkproducts.domain.usecases.PageChangedUseCase
import com.example.vkproducts.domain.usecases.QueryChangedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {
    @Provides
    @Singleton
    fun provideProductsApi() : ProductsApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(api: ProductsApi) : ProductsRepository {
        return ProductsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideListProductsUseCases(repository: ProductsRepository) : ListProductsUseCases {
        return ListProductsUseCases(
            pageChangedUseCase = PageChangedUseCase(repository),
            queryChangedUseCase = QueryChangedUseCase(repository),
            getAllProductsRequest = GetAllProductsRequest(repository)
        )
    }
}