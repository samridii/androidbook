package com.example.booksandbranches.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booksandbranches.R
import com.example.booksandbranches.model.BookDetailModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val context: Context,
    private val books: ArrayList<BookDetailModel> = ArrayList()
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface ProductClickListener {
        fun onAddToCartClicked(book: BookDetailModel, position: Int)
        fun onProductClicked(book: BookDetailModel, position: Int)
    }

    private var listener: ProductClickListener? = null

    fun setOnProductClickListener(listener: ProductClickListener) {
        this.listener = listener
    }

    fun updateBooks(newBooks: List<BookDetailModel>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val book = books[position]

        // Set book details
        holder.bookTitle.text = book.title
        holder.bookDescription.text = book.description

        // Format price with currency symbol
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        holder.bookPrice.text = formatter.format(book.price)

        holder.bookImage.setImageResource(book.imageRes)

        // Set click listeners
        holder.btnAddToCart.setOnClickListener {
            listener?.onAddToCartClicked(book, holder.adapterPosition)
        }

        holder.bookCard.setOnClickListener {
            listener?.onProductClicked(book, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = books.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCard: MaterialCardView = itemView.findViewById(R.id.productCard)
        val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookDescription: TextView = itemView.findViewById(R.id.bookDescription)
        val bookPrice: TextView = itemView.findViewById(R.id.bookPrice)
        val btnAddToCart: MaterialButton = itemView.findViewById(R.id.btnAddToCart)
    }
}