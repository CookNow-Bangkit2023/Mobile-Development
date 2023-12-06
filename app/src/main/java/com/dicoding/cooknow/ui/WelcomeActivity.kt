package com.dicoding.cooknow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivityMainBinding
import com.dicoding.cooknow.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

            val user = Firebase.auth.currentUser
            val intent: Intent

            if (user != null) {
                // User is logged in
                Log.d("MainActivity", "User is logged in with email: ${user.email}")
                intent = Intent(this, MainActivity::class.java)
            } else {
                // User is not logged in
                Log.d("LoginActivity", "User is not logged in")
                intent = Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
            finish()

        binding.buttonScreen.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}