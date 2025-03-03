package com.example.booksandbranches.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booksandbranches.R
import com.example.booksandbranches.databinding.FragmentBookDetailBinding
import com.example.booksandbranches.model.CartModel
import com.example.booksandbranches.model.ProductModel
import com.example.booksandbranches.repository.CartRepositoryImpl
import com.example.booksandbranches.repository.UserRepositoryImpl
import com.example.booksandbranches.utils.LoadingUtils
import com.example.booksandbranches.viewmodel.UserViewModel

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: ProductModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils
    private val cartRepository = CartRepositoryImpl()
    private val userRepository = UserRepositoryImpl()

    private val userId: String?
        get() = userRepository.getCurrentUser()?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel and LoadingUtils
        userViewModel = UserViewModel(userRepository)
        loadingUtils = LoadingUtils(requireContext())

        // Get the selected book from arguments
        arguments?.let {
            product = it.getParcelable("book")!!
            populateBookDetails(product)
        }

        // Handle Add to Cart button click
        binding.btnAddToCart.setOnClickListener {
            if (userId != null) {
                addToCart(product)
            } else {
                Toast.makeText(requireContext(), "Please log in to add items to the cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateBookDetails(product: ProductModel) {
        binding.bookTitle.text = product.productName
        binding.bookAuthor.text = product.author
//        binding.bookDescription.text = product.descripti
        binding.bookPrice.text = "$${product.price}"
        binding.bookImage.setImageResource(product.imageRes)
    }

    private fun addToCart(product: ProductModel) {
        loadingUtils.showLoading()
        val cartModel = CartModel(
            userId = userId!!,
            productId = product.productId,
            quantity = 1,
            price = product.price
        )

        cartRepository.addToCart(cartModel) { success, message ->
            loadingUtils.dismiss()
            if (success) {
                Toast.makeText(requireContext(), "${product.productName} added to cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to add to cart: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}