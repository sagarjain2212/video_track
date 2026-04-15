package com.youtubetrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtubetrack.interfaces.ProductRepository
import com.youtubetrack.model.ProductEntity
import com.youtubetrack.model.ProductUiState
import com.youtubetrack.state.ValidationEvent
import com.youtubetrack.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val repository: ProductRepository // Usually you'd use an 'AddProductUseCase' here
) : ViewModel() {

    private val _state = MutableStateFlow(ProductUiState())
    val state: StateFlow<ProductUiState> = _state

    private val _validationEvents = MutableSharedFlow<ValidationEvent>()
    val validationEvents = _validationEvents.asSharedFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { productList ->
                _state.update { it.copy(products = productList) }
            }
        }
    }

    fun onAddProductClick() {
        viewModelScope.launch {
            val newProduct = ProductEntity(
                id = (0..1000).random(),
                title = "Offline Product",
                price = 10.99,
                description = "Saved in Room!",
                category = "Test",
                image = ""
            )
            repository.addProduct(newProduct)
        }
    }

    fun singleItemInsert(title: String, category: String) {
        // 1. Validate
        if (title.isBlank()) {
            viewModelScope.launch { _validationEvents.emit(ValidationEvent.Error("Title cannot be empty")) }
            return
        }
        if (category.isBlank()) {
            viewModelScope.launch { _validationEvents.emit(ValidationEvent.Error("Please select a category")) }
            return
        }

        // 2. Save
        viewModelScope.launch(Dispatchers.IO) {
            val newProduct = ProductEntity(
                id = (0..10000).random(), // Increased range to avoid collisions
                title = title.trim(),
                price = 10.99,
                description = "Manually added item",
                category = category,
                image = ""
            )
            repository.addProduct(newProduct)
            _validationEvents.emit(ValidationEvent.Success)
        }
    }
}