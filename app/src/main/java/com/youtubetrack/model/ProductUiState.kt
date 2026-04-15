package com.youtubetrack.model

data class ProductUiState(
    val products: List<ProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)