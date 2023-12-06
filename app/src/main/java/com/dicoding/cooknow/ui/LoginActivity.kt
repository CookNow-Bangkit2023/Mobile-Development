package com.dicoding.cooknow.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val goRegisterText = getString(R.string.register)
        val spannableString = SpannableString(goRegisterText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, 0, goRegisterText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)

        val color = Color.parseColor("#FF9C00")
        spannableString.setSpan(ForegroundColorSpan(color), 0, goRegisterText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.goRegister.text = spannableString
        binding.goRegister.movementMethod = LinkMovementMethod.getInstance()
    }
}