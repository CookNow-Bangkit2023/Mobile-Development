package com.dicoding.cooknow.ui.detailRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R

class ProcedureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_procedure, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_procedure)

        val procedureList = ArrayList<String>()
        procedureList.add("Step 1")
        procedureList.add("Step 2")
        procedureList.add("Step 3")

        val adapter = ProcedureAdapter(procedureList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    class ProcedureAdapter(private val procedure: List<String>) :
        RecyclerView.Adapter<ProcedureAdapter.ProcedureViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_procedure, parent, false)
            return ProcedureViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProcedureViewHolder, position: Int) {
            holder.bind(procedure[position])
        }

        override fun getItemCount(): Int = procedure.size

        inner class ProcedureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val procedureTextView: TextView = itemView.findViewById(R.id.tv_step)

            fun bind(step: String) {
                procedureTextView.text = step
            }
        }
    }

}
