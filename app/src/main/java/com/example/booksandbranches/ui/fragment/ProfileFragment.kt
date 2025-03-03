package com.example.booksandbranches.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booksandbranches.databinding.FragmentProfileBinding
import com.example.booksandbranches.model.UserModel
import com.example.booksandbranches.repository.UserRepositoryImpl
import com.example.booksandbranches.ui.activity.LoginActivity
import com.example.booksandbranches.utils.LoadingUtils
import com.example.booksandbranches.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var currentUser: UserModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)
        loadingUtils = LoadingUtils(requireContext())

        // Fetch current user details
        fetchCurrentUserDetails()

        // Set up Save Button
        binding.profileSaveButton.setOnClickListener {
            updateUserProfile()
        }

        // Set up Delete Button
        binding.profileDeleteButton.setOnClickListener {
            deleteUserProfile()
        }
    }

    private fun fetchCurrentUserDetails() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            loadingUtils.showLoading()
            userViewModel.getCurrentUserDetails(userId) { userModel ->
                loadingUtils.dismiss()
                if (userModel != null) {
                    currentUser = userModel
                    populateUserDetails(userModel)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch user details", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateUserDetails(userModel: UserModel) {
        binding.profileFirstName.setText(userModel.firstName)
        binding.profileLastName.setText(userModel.lastName)
        binding.profileEmail.setText(userModel.email)
        binding.profileAddress.setText(userModel.address)
        binding.profilePhone.setText(userModel.phoneNumber)
    }

    private fun updateUserProfile() {
        val firstName = binding.profileFirstName.text.toString().trim()
        val lastName = binding.profileLastName.text.toString().trim()
        val address = binding.profileAddress.text.toString().trim()
        val phone = binding.profilePhone.text.toString().trim()

        if (validateInputs(firstName, lastName, address, phone)) {
            loadingUtils.showLoading()
            val updatedUser = currentUser.copy(
                firstName = firstName,
                lastName = lastName,
                address = address,
                phoneNumber = phone
            )

            userViewModel.updateUserProfile(updatedUser) { success, message ->
                loadingUtils.dismiss()
                if (success) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteUserProfile() {
        loadingUtils.showLoading()
        userViewModel.deleteUserProfile { success, message ->
            loadingUtils.dismiss()
            if (success) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                // Sign out from Firebase Auth
                FirebaseAuth.getInstance().signOut()

                // Navigate to login screen
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(firstName: String, lastName: String, address: String, phone: String): Boolean {
        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate first name (letters only, more than 2 characters)
        if (!firstName.matches("[a-zA-Z]+".toRegex()) || firstName.length <= 2) {
            Toast.makeText(requireContext(), "First name must contain only letters and be at least 3 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate last name (letters only)
        if (!lastName.matches("[a-zA-Z]+".toRegex())) {
            Toast.makeText(requireContext(), "Last name must contain only letters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate address (contains letters and is more than 4 characters)
        if (!address.contains("[a-zA-Z]".toRegex()) || address.length <= 4) {
            Toast.makeText(requireContext(), "Address must contain letters and be at least 5 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validate phone number (10 digits only)
        if (phone.length != 10 || !phone.matches("\\d+".toRegex())) {
            Toast.makeText(requireContext(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}