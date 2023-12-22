package com.dicoding.cooknow.ui.detailRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.FragmentProcedureBinding
import com.dicoding.cooknow.ui.model.ProceduresViewModel

class ProcedureFragment : Fragment() {

    private lateinit var proceduresViewModel: ProceduresViewModel
    private lateinit var binding: FragmentProcedureBinding

    companion object {
        private const val ARG_RECIPE_ID = "recipe_id"

        fun newInstance(recipeId: Int): ProcedureFragment {
            val fragment = ProcedureFragment()
            val args = Bundle()
            args.putInt(ARG_RECIPE_ID, recipeId)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProcedureBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_procedure)

        proceduresViewModel = ViewModelProvider(this)[ProceduresViewModel::class.java]

        val recipeId = arguments?.getInt(ARG_RECIPE_ID, -1) ?: -1
        proceduresViewModel.getProcedure(recipeId)

        val adapter = ProcedureAdapter(emptyList(), recipeId)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        proceduresViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        proceduresViewModel.getProcedure(recipeId)

        proceduresViewModel.proceduresList.observe(viewLifecycleOwner){ procedures ->
            adapter.updateData(procedures)
        }

        return view
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    class ProcedureAdapter(private var procedures: List<String>, private val recipeId: Int) :
        RecyclerView.Adapter<ProcedureAdapter.ProcedureViewHolder>() {

        fun updateData(newData: List<String>) {
            procedures = newData
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_procedure, parent, false)
            return ProcedureViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProcedureViewHolder, position: Int) {
            holder.bind(procedures[position], position + 1, recipeId)
        }

        override fun getItemCount(): Int = procedures.size

        inner class ProcedureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val stepTextView: TextView = itemView.findViewById(R.id.tv_step)
            private val procedureTextView: TextView = itemView.findViewById(R.id.tv_description)

            fun bind(step: String, position: Int, recipeId: Int) {
                // Step label yang dinamis berdasarkan posisi
                stepTextView.text = "Step $position"
                procedureTextView.text = step
            }
        }
    }

}
