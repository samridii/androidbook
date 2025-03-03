package com.example.booksandbranches.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.booksandbranches.R
import com.example.booksandbranches.databinding.ActivityForgetPasswordBinding
import com.example.booksandbranches.repository.UserRepositoryImpl
import com.example.booksandbranches.viewmodel.UserViewModel

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewBinding
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel with repository
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Handle "Forgot Password" button click
        binding.btnForget.setOnClickListener {
            val email = binding.editEmailForget.text.toString().trim()

            if (validateEmail(email)) {
                userViewModel.forgetPassword(email) { success, message ->
                    if (success) {
                        Toast.makeText(this@ForgetPasswordActivity, message, Toast.LENGTH_LONG).show()
                        finish() // Close the activity after successful password reset
                    } else {
                        Toast.makeText(this@ForgetPasswordActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Handle "Back to Login" button click
        binding.btnBackToLogin.setOnClickListener {
            startActivity(Intent(this@ForgetPasswordActivity, LoginActivity::class.java))
            finish() // Close the current activity
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to validate email input
    private fun validateEmail(email: String): Boolean {
        // Check if email is empty
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}