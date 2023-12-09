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
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        customSpan()
        showPassword()

        binding.registerButton.setOnClickListener {
            val name = binding.edtNameRegister.text.toString()
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()
            val confirmPassword = binding.edtConfirmpasswordRegister.text.toString()

            // Validasi Name
            if (name.isEmpty()){
                binding.edtNameRegister.error = getString(R.string.name_empty)
                binding.edtNameRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi panjang name
            if (name.length < 4){
                binding.edtNameRegister.error = getString(R.string.name_length)
                binding.edtNameRegister.requestFocus()
                return@setOnClickListener
            }

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

            if (!confirmPassword.equals(password)){
                binding.edtConfirmpasswordRegister.error = getString(R.string.confirm_password_invalid)
                binding.edtConfirmpasswordRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(name, email, password)
        }
    }

    private fun RegisterFirebase(name: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    val userID = auth.currentUser!!.uid
                    db.collection("users").document(userID).set(user)
                        .addOnSuccessListener {
                            Log.d(TAG, "onSuccess: user Profile is created for : $userID")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "onFailure: Error created Profile", e)
                        }
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
        val confirmpasswordEditText: TextInputEditText = binding.edtConfirmpasswordRegister
        val passwordVisibleCheckBox: CheckBox = binding.passwordVisible

        passwordVisibleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Pengecekan menggunakan Checkbox
            if (isChecked) {
                // Jika Checkbox dicentang, tampilkan teks password
                passwordEditText.transformationMethod = null
                confirmpasswordEditText.transformationMethod = null
            } else {
                // Jika Checkbox tidak dicentang, sembunyikan teks password
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                confirmpasswordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }

}