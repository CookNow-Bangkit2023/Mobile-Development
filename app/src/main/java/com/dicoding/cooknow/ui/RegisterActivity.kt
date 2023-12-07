package com.dicoding.cooknow.ui

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
import com.dicoding.cooknow.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        customSpan()
        showPassword()

        binding.registerButton.setOnClickListener {
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()

            // Validasi Email
            if (email.isEmpty()){
                binding.edtEmailRegister.error = getString(R.string.email_empty)
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi Email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailRegister.error = getString(R.string.email_invalid)
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi Password
            if (password.isEmpty()){
                binding.edtPasswordRegister.error = getString(R.string.password_empty)
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi panjang password
            if (password.length < 8){
                binding.edtPasswordRegister.error = getString(R.string.password_length)
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(email, password)
        }
    }

    private fun RegisterFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun customSpan(){
        val registerText = binding.askRegister

        val goRegisterText = getString(R.string.login)
        val spannableString = SpannableString(goRegisterText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        spannableString.setSpan(clickableSpan, 0, goRegisterText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)

        val color = Color.parseColor("#FF9C00")
        spannableString.setSpan(ForegroundColorSpan(color), 0, goRegisterText.length, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE)

        registerText.text = TextUtils.concat(getString(R.string.ask_register)," ", spannableString)
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun showPassword(){
        val passwordEditText: TextInputEditText = binding.edtPasswordRegister
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