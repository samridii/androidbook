package com.example.booksandbranches.repository

import com.example.booksandbranches.model.CartModel
import com.example.booksandbranches.model.ProductModel

interface BookDetailRepository {
    // Fetch book details by ID
    fun getBookById(bookId: String, callback: (ProductModel?) -> Unit)

    // Add a book to the cart
    fun addToCart(cartModel: CartModel, callback: (Boolean) -> Unit)
}