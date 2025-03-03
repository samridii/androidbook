package com.example.booksandbranches.repository

import com.example.booksandbranches.R
import com.example.booksandbranches.model.ProductModel

class ProductRepositoryImpl : ProductRepository {
    // Sample book data
    private val sampleBooks = mapOf(
        "1" to ProductModel("1", "Steal Like an Artist", "Austin Kleon", 12.99, R.drawable.one,),
        "2" to ProductModel("2", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 15.99, R.drawable.two),
        "3" to ProductModel("3", "Paradise Lost", "John Milton", 10.99, R.drawable.three),
        "4" to ProductModel("4", "Thinking, Fast and Slow", "Daniel Kahneman", 14.99, R.drawable.four),
        "5" to ProductModel("5", "Red, White & Royal Blue", "Casey McQuiston", 13.99, R.drawable.five),
        "6" to ProductModel("6", "At the End of the World, There Is a Pond", "Steven Duong", 11.99, R.drawable.six),
        "7" to ProductModel("7", "Tuesdays with Morrie", "Mitch Albom", 9.99, R.drawable.seven),
        "8" to ProductModel("8", "The Adventures of Tom Sawyer", "Mark Twain", 8.99, R.drawable.eight),
        "9" to ProductModel("9", "The Silent Patient", "Alex Michaelides", 12.99, R.drawable.nine),
        "10" to ProductModel("10", "The Da Vinci Code", "Dan Brown", 10.99, R.drawable.ten)
    )

    override fun getProductById(productId: String, callback: (ProductModel?, Boolean) -> Unit) {
        val book = sampleBooks[productId]
        callback(book, book != null)
    }
}
