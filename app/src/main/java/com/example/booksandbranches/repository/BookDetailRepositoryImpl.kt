package com.example.booksandbranches.repository

import com.example.booksandbranches.model.CartModel
import com.example.booksandbranches.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailRepositoryImpl : BookDetailRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getBookById(bookId: String, callback: (ProductModel?) -> Unit) {
        db.collection("books")
            .document(bookId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val book = document.toObject(ProductModel::class.java)
                    if (book != null) {
                        callback(book)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                callback(null)
            }
    }

    override fun addToCart(cartModel: CartModel, callback: (Boolean) -> Unit) {
        db.collection("carts")
            .add(cartModel)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { exception ->
                callback(false)
            }
    }
}