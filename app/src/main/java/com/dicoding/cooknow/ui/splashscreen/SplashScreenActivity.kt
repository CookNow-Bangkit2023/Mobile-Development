package com.dicoding.cooknow.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivitySplashScreenBinding
import com.dicoding.cooknow.ui.main.MainActivity
import com.dicoding.cooknow.ui.welcome.WelcomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var motionlayout: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        motionlayout = findViewById(R.id.mainlayout)
        motionlayout.startLayoutAnimation()

        motionlayout.setTransitionListener(object :MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                Handler().postDelayed({
                    val user = Firebase.auth.currentUser
                    val intent: Intent

                    if (user != null) {
                        // User is logged in
                        Log.d("MainActivity", "User is logged in with email: ${user.email}")
                        intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    } else {
                        // User is not logged in
                        Log.d("WelcomeActivity", "User is not logged in")
                        intent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
                    }

                    startActivity(intent)
                    finish()
                }, 500)
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })


    }
}