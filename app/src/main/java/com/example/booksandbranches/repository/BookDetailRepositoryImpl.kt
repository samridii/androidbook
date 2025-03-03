package com.example.booksandbranches.repository

import com.example.booksandbranches.model.CartModel
import com.example.booksandbranches.model.ProductModel
import com.example.booksandbranches.utils.Result
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailRepositoryImpl : BookDetailRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getBookById(bookId: String, callback: (Result<ProductModel>) -> Unit) {
        db.collection("books")
            .document(bookId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val book = document.toObject(ProductModel::class.java)
                    if (book != null) {
                        callback(Result.success(book))
                    } else {
                        callback(Result.Error("Failed to parse book data"))
                    }
                } else {
                    callback(Result.Error("Book not found"))
                }
            }
            .addOnFailureListener { exception ->
                callback(Result.Error(exception.message ?: "Failed to fetch book details"))
            }
    }

    override fun addToCart(cartModel: CartModel, callback: (Result<Boolean>) -> Unit) {
        db.collection("carts")
            .add(cartModel)
            .addOnSuccessListener {
                callback(Result.success(true))
            }
            .addOnFailureListener { exception ->
                callback(Result.Error(exception.message ?: "Failed to add to cart"))
            }
    }
}