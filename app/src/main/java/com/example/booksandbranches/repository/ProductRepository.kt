package com.example.booksandbranches.repository

import com.example.booksandbranches.model.ProductModel

interface ProductRepository {
    fun getProductById(productId: String, callback: (ProductModel?, Boolean) -> Unit)
}