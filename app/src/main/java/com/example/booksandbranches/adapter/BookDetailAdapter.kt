package com.example.booksandbranches.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booksandbranches.R
import com.example.booksandbranches.model.BookDetailModel

class BookDetailAdapter(
    private val context: Context,
    private val book: BookDetailModel // Single book to display
) : RecyclerView.Adapter<BookDetailAdapter.BookDetailViewHolder>() {

    interface OnAddToCartClickListener {
        fun onAddToCartClick(book: BookDetailModel)
    }

    private var listener: OnAddToCartClickListener? = null

    fun setOnAddToCartClickListener(listener: OnAddToCartClickListener) {
        this.listener = listener
    }

    // ViewHolder for book details
    class BookDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        val bookDescription: TextView = itemView.findViewById(R.id.bookDescription)
        val bookPrice: TextView = itemView.findViewById(R.id.bookPrice)
        val btnAddToCart: Button = itemView.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookdetail, parent, false)
        return BookDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookDetailViewHolder, position: Int) {
        // Bind book data to views
        holder.bookTitle.text = book.title
        holder.bookAuthor.text = book.author
        holder.bookDescription.text = book.description
        holder.bookPrice.text = "$${book.price}"

        // Load book image
        if (book.imageRes > 0) {
            holder.bookImage.setImageResource(book.imageRes)
        } else {
            holder.bookImage.setImageResource(R.drawable.books) // Default book image
        }

        // Handle "Add to Cart" button click
        holder.btnAddToCart.setOnClickListener {
            listener?.onAddToCartClick(book)
        }
    }

    override fun getItemCount(): Int = 1 // Only one item (the book details)
}