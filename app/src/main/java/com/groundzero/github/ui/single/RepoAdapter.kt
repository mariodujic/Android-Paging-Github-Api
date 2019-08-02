package com.groundzero.github.ui.single

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.github.R
import com.groundzero.github.data.remote.Contributor

class RepoAdapter(private val context: Context, private val contributors: List<Contributor>) :
    RecyclerView.Adapter<RepoAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(context), parent)
    }

    override fun getItemCount(): Int {
        return contributors.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(contributors[position])
    }

    class CustomViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_contributor, parent, false)) {

        private val name = itemView.findViewById<TextView>(R.id.contributor_name)

        fun bind(contributor: Contributor) {
            name.text = contributor.name
        }
    }
}