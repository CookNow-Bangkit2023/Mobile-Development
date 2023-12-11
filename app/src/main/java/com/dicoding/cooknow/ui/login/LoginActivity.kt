package com.dicoding.cooknow.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivityLoginBinding
import com.dicoding.cooknow.ui.register.RegisterActivity
import com.dicoding.cooknow.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        customSpan()
        showPassword()

        binding.loginButton.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            // Validasi Email
            if (email.isEmpty()){
                binding.edtEmailLogin.error = getString(R.string.email_empty)
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            // Validasi Email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailLogin.error = getString(R.string.email_invalid)
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            // Validasi Password
            if (password.isEmpty()){
                binding.edtPasswordLogin.error = getString(R.string.password_empty)
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, getString(R.string.login_success) + " $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun customSpan(){
        val registerText = binding.askLogin

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

        registerText.text = TextUtils.concat(getString(R.string.ask_login)," ", spannableString)
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun showPassword(){
        val passwordEditText: TextInputEditText = binding.edtPasswordLogin
        val passwordVisibleCheckBox: CheckBox = binding.passwordVisible

        passwordVisibleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Pengecekan menggunakan Checkbox
            if (isChecked) {
                // Jika Checkbox dicentang, tampilkan teks password
                passwordEditText.transformationMethod = null
            } else {
                // Jika Checkbox tidak dicentang, sembunyikan teks password
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}