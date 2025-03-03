package com.example.booksandbranches.viewmodel

import androidx.lifecycle.ViewModel
import com.example.booksandbranches.model.UserModel
import com.example.booksandbranches.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    // Log in a user using Firebase authentication
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    // Sign up a new user and return a status message and userId
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.signup(email, password, callback)
    }

    // Add user details to the Firebase Realtime Database
    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, userModel, callback)
    }

    // Initiate a password reset for the given email address
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    // Retrieve the current Firebase user
    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }

    // Fetch the current user's details from the database
    fun getCurrentUserDetails(userId: String, callback: (UserModel?) -> Unit) {
        repo.getCurrentUserDetails(userId, callback)
    }

    // Update the user's profile information in the database
    fun updateUserProfile(userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.updateUserProfile(userModel, callback)
    }

    // Delete the user's profile from the database
    fun deleteUserProfile(callback: (Boolean, String) -> Unit) {
        repo.deleteUserProfile(callback)
    }
}


