package com.groundzero.github.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.groundzero.github.R
import com.groundzero.github.data.remote.Github

class ListPageAdapter(private val context: Context, private val listItem: ListItem) :
    PagedListAdapter<Github, ListPageAdapter.GithubViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        return GithubViewHolder(LayoutInflater.from(context), parent, listItem)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        val github: Github? = getItem(position)
        holder.bind(github!!)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Github>() {
            override fun areItemsTheSame(oldItem: Github, newItem: Github) = oldItem.fullName == newItem.fullName
            override fun areContentsTheSame(oldItem: Github, newItem: Github) = oldItem == newItem
        }
    }

    class GithubViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup, private val listItem: ListItem) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_repository, parent, false)) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val fullName: TextView = itemView.findViewById(R.id.full_name)

        fun bind(github: Github) {
            name.text = github.name
            fullName.text = github.fullName
            itemView.setOnClickListener(listItem.onItemClickListener(github))
        }
    }
}