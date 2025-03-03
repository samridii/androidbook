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
import com.example.booksandbranches.databinding.ActivityLoginBinding
import com.example.booksandbranches.repository.UserRepositoryImpl
import com.example.booksandbranches.utils.LoadingUtils
import com.example.booksandbranches.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel with repository
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Initialize LoadingUtils
        loadingUtils = LoadingUtils(this)

        // Handle "Login" button click
        binding.btnLogin.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                loadingUtils.showLoading() // Show loading dialog
                userViewModel.login(email, password) { success: Boolean, message: String ->
                    loadingUtils.dismiss() // Hide loading dialog
                    if (success) {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, NavigationActivity::class.java))
                        finish() // Close the current activity
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Handle "Sign Up" button click
        binding.btnSignupnavigate.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        // Handle "Forgot Password" button click
        binding.btnForget.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to validate email and password inputs
    private fun validateInputs(email: String, password: String): Boolean {
        // Check if email is empty
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }

        // Check if password is empty
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
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