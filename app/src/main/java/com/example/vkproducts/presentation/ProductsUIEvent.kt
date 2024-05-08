package com.example.vkproducts.presentation

sealed class ProductsUIEvent {
    data object Init: ProductsUIEvent()
    data class QueryChanged(val query: String) : ProductsUIEvent()
    data class PageChanged(val page: Int) : ProductsUIEvent()
//    data class ProductClicked(val id: Int) : ProductsUIEvent()
}