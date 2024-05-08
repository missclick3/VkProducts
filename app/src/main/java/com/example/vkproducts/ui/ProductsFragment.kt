package com.example.vkproducts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkproducts.R
import com.example.vkproducts.core.asFlow
import com.example.vkproducts.databinding.FragmentProductsBinding
import com.example.vkproducts.presentation.ListProductsViewModel
import com.example.vkproducts.presentation.ProductsResult
import com.example.vkproducts.presentation.ProductsUIEvent
import com.example.vkproducts.presentation.recyclerPages.PagesAdapter
import com.example.vkproducts.presentation.recyclerProducts.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products){
    private val productsAdapter: ProductsAdapter by lazy { ProductsAdapter() }
    private val pagesAdapter: PagesAdapter by lazy { PagesAdapter(clickListener) }
    private val viewModel: ListProductsViewModel by viewModels()
    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerProducts.adapter = productsAdapter
        binding.recyclerPages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPages.adapter = pagesAdapter
        lifecycleScope.launch {
            binding.etSearch.asFlow().collect {
                viewModel.onEvent(ProductsUIEvent.QueryChanged(it))
            }
        }
        setupChannelResults()
    }



    private fun setupChannelResults() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.productsResult.collect {result ->
                when(result) {
                    is ProductsResult.Error -> {
                        binding.loader.isVisible = false
                        binding.recyclerProducts.isVisible = true
                        binding.etSearch.isVisible = true
                        binding.recyclerPages.isVisible = true
                        Toast.makeText(requireContext(), result.errorText, Toast.LENGTH_LONG).show()
                    }
                    ProductsResult.Loading -> {
                        binding.loader.isVisible = true
                        binding.recyclerProducts.isVisible = false
                        binding.recyclerPages.isVisible = false
                    }
                    is ProductsResult.ProductsList -> {
                        binding.loader.isVisible = false
                        binding.recyclerProducts.isVisible = true
                        binding.etSearch.isVisible = true
                        binding.recyclerPages.isVisible = true
                        productsAdapter.setProducts(result.items)
                        pagesAdapter.setPages(result.pages)
                    }
                }
            }
        }
    }

    private fun doOnClick(pageNumber: Int) {
        binding.recyclerPages.let { rv ->
            viewModel.onEvent(ProductsUIEvent.PageChanged(pageNumber))
        }
    }

    private val clickListener = object : PagesAdapter.OnRecyclerItemClicked {
        override fun onClick(pagesNumber: Int, view: View) {
            resetBackgroundColors()
            doOnClick(pagesNumber)
        }
    }

    private fun resetBackgroundColors() {
        for (i in 0 until binding.recyclerPages.childCount) {
            val itemView = binding.recyclerPages.getChildAt(i)
            itemView.setBackgroundColor(android.graphics.Color.WHITE)
        }
    }
}