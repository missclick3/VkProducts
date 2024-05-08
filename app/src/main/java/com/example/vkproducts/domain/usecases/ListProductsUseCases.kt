package com.example.vkproducts.domain.usecases

data class ListProductsUseCases(
    val pageChangedUseCase: PageChangedUseCase,
    val queryChangedUseCase: QueryChangedUseCase,
    val getAllProductsRequest: GetAllProductsRequest
)
