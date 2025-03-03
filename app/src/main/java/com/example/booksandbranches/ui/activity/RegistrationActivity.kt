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
import com.example.booksandbranches.databinding.ActivityRegistrationBinding
import com.example.booksandbranches.model.UserModel
import com.example.booksandbranches.repository.UserRepositoryImpl
import com.example.booksandbranches.utils.LoadingUtils
import com.example.booksandbranches.viewmodel.UserViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewBinding
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize LoadingUtils and ViewModel
        loadingUtils = LoadingUtils(this)
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Handle "Sign Up" button click
        binding.signUp.setOnClickListener {
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val firstName = binding.registerFname.text.toString().trim()
            val lastName = binding.registerLName.text.toString().trim()
            val address = binding.registerAddress.text.toString().trim()
            val phone = binding.registerContact.text.toString().trim()

            if (validateInputs(email, password, firstName, lastName, address, phone)) {
                loadingUtils.show() // Show loading dialog
                userViewModel.signup(email, password) { success, message, userId ->
                    if (success) {
                        val userModel = UserModel(userId, firstName, lastName, address, phone, email)
                        userViewModel.addUserToDatabase(userId, userModel) { success, message ->
                            loadingUtils.dismiss() // Hide loading dialog
                            if (success) {
                                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                                finish() // Close the current activity
                            } else {
                                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        loadingUtils.dismiss() // Hide loading dialog
                        Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Handle "Login" button click
        binding.btnLoginNavigate.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            finish() // Close the current activity
        }

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to validate user inputs
    private fun validateInputs(
        email: String, password: String, firstName: String,
        lastName: String, address: String, phone: String
    ): Boolean {
        // Check if fields are empty
        if (firstName.isEmpty()) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lastName.isEmpty()) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter your delivery address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate first name - only letters allowed and at least 3 characters
        if (!firstName.matches("[a-zA-Z]+".toRegex()) || firstName.length < 3) {
            Toast.makeText(this, "Enter a valid and complete first name", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate last name - only letters allowed
        if (!lastName.matches("[a-zA-Z]+".toRegex())) {
            Toast.makeText(this, "Last name must contain only letters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate password - at least 8 characters with at least one letter and one number
        if (password.length < 8 || !password.matches(".*[A-Za-z].*".toRegex()) ||
            !password.matches(".*[0-9].*".toRegex())
        ) {
            Toast.makeText(
                this, "Password must be at least 8 characters with letters and numbers",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        // Validate phone number - must be exactly 10 digits
        if (phone.length != 10 || !phone.matches("\\d+".toRegex())) {
            Toast.makeText(this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate address - must be at least 5 characters and contain at least one letter
        if (address.length <= 5 || !address.matches(".*[a-zA-Z].*".toRegex())) {
            Toast.makeText(
                this, "Please enter a valid lengthier delivery address (no special characters)",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }
}