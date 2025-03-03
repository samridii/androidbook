package com.example.booksandbranches.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booksandbranches.R
import com.example.booksandbranches.adapter.ProductAdapter
import com.example.booksandbranches.databinding.FragmentHomeBinding
import com.example.booksandbranches.model.ProductModel
import com.example.booksandbranches.model.CartModel
import com.example.booksandbranches.repository.CartRepositoryImpl
import com.example.booksandbranches.repository.UserRepositoryImpl

class HomeFragment : Fragment(), ProductAdapter.ProductClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter
    private val cartRepository = CartRepositoryImpl() // Directly use CartRepositoryImpl
    private val userRepository = UserRepositoryImpl()

    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch the current user ID
        userId = userRepository.getCurrentUser()?.uid

        setupRecyclerView()
        loadProducts()

        // Handle refresh action
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadProducts()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(requireContext())
        productAdapter.setOnProductClickListener(this)

        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(context, 2) // 2 columns for grid layout
            adapter = productAdapter
        }
    }

    private fun loadProducts() {
        // Sample book data with titles, authors, and drawable images
        val products = arrayListOf(
            ProductModel("1", "Steal Like an Artist", "Austin Kleon", 12.99, R.drawable.one),
            ProductModel("2", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 15.99, R.drawable.two),
            ProductModel("3", "Paradise Lost", "John Milton", 10.99, R.drawable.three),
            ProductModel("4", "Thinking, Fast and Slow", "Daniel Kahneman", 14.99, R.drawable.four),
            ProductModel("5", "Red, White & Royal Blue", "Casey McQuiston", 13.99, R.drawable.five),
            ProductModel("6", "At the End of the World, There Is a Pond", "Steven Duong", 11.99, R.drawable.six),
            ProductModel("7", "Tuesdays with Morrie", "Mitch Albom", 9.99, R.drawable.seven),
            ProductModel("8", "The Adventures of Tom Sawyer", "Mark Twain", 8.99, R.drawable.eight),
            ProductModel("9", "The Silent Patient", "Alex Michaelides", 12.99, R.drawable.nine),
            ProductModel("10", "The Da Vinci Code", "Dan Brown", 10.99, R.drawable.ten)
        )

        // Update adapter with new data
        productAdapter.updateProducts(products)

        // Show/hide empty state
        toggleEmptyState(products.isEmpty())
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.recyclerViewProducts.visibility = View.GONE
        } else {
            binding.emptyStateLayout.visibility = View.GONE
            binding.recyclerViewProducts.visibility = View.VISIBLE
        }
    }

    override fun onAddToCartClicked(product: ProductModel, position: Int) {
        // Ensure the user is logged in
        if (userId == null) {
            Toast.makeText(requireContext(), "Please log in to add items to the cart", Toast.LENGTH_SHORT).show()
            return
        }

        val cartModel = CartModel(
            userId = userId!!, // Use the dynamically fetched userId
            productId = product.productId,
            quantity = 1,
            price = product.price
        )

        // Use CartRepositoryImpl directly
        cartRepository.addToCart(cartModel) { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "${product.productName} added to cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to add to cart: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onProductClicked(product: ProductModel, position: Int) {
        Toast.makeText(requireContext(), "Viewing ${product.productName}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}