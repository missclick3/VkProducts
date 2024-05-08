package com.example.vkproducts.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkproducts.domain.usecases.ListProductsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    private val useCases: ListProductsUseCases
) : ViewModel(){

    private var state by mutableStateOf(ListProductsState())

    private val resultChannel = Channel<ProductsResult>()
    val productsResult = resultChannel.receiveAsFlow()

    init {
        onEvent(ProductsUIEvent.Init)
    }

    fun onEvent(event: ProductsUIEvent) {
        when (event) {
            ProductsUIEvent.Init -> getProducts()
            is ProductsUIEvent.PageChanged -> changePage(event.page)
            is ProductsUIEvent.QueryChanged -> loadProducts(event.query)
        }
    }

    private fun loadProducts(query: String) {
        viewModelScope.launch {
            resultChannel.send(ProductsResult.Loading)
            val result = useCases.queryChangedUseCase.execute(query)
            resultChannel.send(result)
            if (result is ProductsResult.ProductsList) {
                state = state.copy(query = query, page = result.page)
            }
        }
    }

    private fun changePage(page: Int) {
        viewModelScope.launch {
            resultChannel.send(ProductsResult.Loading)
            val result = useCases.pageChangedUseCase.execute(page, state.query)
            resultChannel.send(result)
            if (result is ProductsResult.ProductsList) {
                state = state.copy(page = page)
            }
        }
    }

    private fun getProducts() {

        viewModelScope.launch {
            resultChannel.send(ProductsResult.Loading)
            val result = useCases.getAllProductsRequest.execute()
            if (result is ProductsResult.ProductsList) {
                Log.d("Get products", "${result.items.size}")
            }
            resultChannel.send(result)
        }
    }

}