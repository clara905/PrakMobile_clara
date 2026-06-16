package com.app.SIAKAD.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.SIAKAD.data.model.MenuAkademik
import com.app.SIAKAD.databinding.ItemMenuAkademikListBinding

class MenuAkademikListAdapter(
    private val items: List<MenuAkademik>,
    private val onItemClick: (MenuAkademik) -> Unit
) : RecyclerView.Adapter<MenuAkademikListAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemMenuAkademikListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuAkademik) {
            binding.tvMenuTitle.text = item.title
            binding.tvMenuDescription.text = item.description
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuAkademikListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}