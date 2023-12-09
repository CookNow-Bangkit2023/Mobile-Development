package com.dicoding.cooknow.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dicoding.cooknow.R

class FindRecipesFragment : Fragment() {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_find_recipes, container, false)

        // Temukan button dengan ID recipesButton
        val recipesButton: Button = rootView.findViewById(R.id.recipesButton)

        // Set listener untuk button
        recipesButton.setOnClickListener {
            // Intent untuk menuju ke ListRecipesActivity
            val intent = Intent(requireActivity(), ListRecipesActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            FindRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
