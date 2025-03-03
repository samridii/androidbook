package com.example.booksandbranches.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.booksandbranches.R
import com.example.booksandbranches.databinding.ActivityNavigationBinding
import com.example.booksandbranches.ui.fragment.BookDetailFragment
import com.example.booksandbranches.ui.fragment.CartFragment
import com.example.booksandbranches.ui.fragment.HomeFragment
import com.example.booksandbranches.ui.fragment.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Default fragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Bottom Navigation setup
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.bookdetail-> replaceFragment(BookDetailFragment())
                R.id.cartItemCard -> replaceFragment(CartFragment())
                R.id.profileCard -> {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        replaceFragment(ProfileFragment())
                    } else {
                        showToast("Please log in to access your profile")
                    }
                }
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}