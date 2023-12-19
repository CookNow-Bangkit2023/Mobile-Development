package com.dicoding.cooknow.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.FragmentProfileBinding
import com.dicoding.cooknow.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(requireContext())

        binding.logoutButton.setOnClickListener {
            builder.setTitle(R.string.logout_title)
            builder.setMessage(R.string.logoutMessage)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    auth.signOut()
                }
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            builder.setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
            builder.create()
        }

        showIdentity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showIdentity(){
        val nameText = binding.usernameTitle
        val emailText = binding.card

        val userID =  auth.currentUser!!.uid
        val ref = db.collection("users").document(userID)
        ref.get().addOnSuccessListener {
            if (it != null){
                val name = it.data?.get("name")?.toString()
                val email = it.data?.get("email")?.toString()

                nameText.text = name
                emailText.text = email
            }
        }
    }
}