package com.example.booksandbranches.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        enableEdgeToEdge()

        // Initialize ViewBinding
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the default fragment to HomeFragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Set up bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            try {
                when (menuItem.itemId) {
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.bookdetail -> replaceFragment(BookDetailFragment())
                    R.id.cartItemCard-> replaceFragment(CartFragment())
                    R.id.profileCard -> {
                        // Check if the user is logged in before navigating to the ProfileFragment
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            replaceFragment(ProfileFragment())
                        } else {
                            Toast.makeText(this, "Please log in to view your profile", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> return@setOnItemSelectedListener false
                }
                true
            } catch (e: Exception) {
                // Show a toast message if an error occurs
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                false
            }
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Simplified function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}