package com.dicoding.cooknow.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.dicoding.cooknow.databinding.ActivitySplashScreenBinding
import com.dicoding.cooknow.ui.main.MainActivity
import com.dicoding.cooknow.ui.welcome.WelcomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val user = Firebase.auth.currentUser
            val intent: Intent

            if (user != null) {
                // User is logged in
                Log.d("MainActivity", "User is logged in with email: ${user.email}")
                intent = Intent(this, MainActivity::class.java)
            } else {
                // User is not logged in
                Log.d("WelcomeActivity", "User is not logged in")
                intent = Intent(this, WelcomeActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 3000)
    }
}