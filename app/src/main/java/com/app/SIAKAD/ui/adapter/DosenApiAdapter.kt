package com.app.SIAKAD.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.SIAKAD.data.model.Dosen
import com.app.SIAKAD.databinding.ItemDosenApiBinding

class DosenApiAdapter(
    private val items: MutableList<Dosen>,
    private val onDeleteClick: (Dosen) -> Unit
) : RecyclerView.Adapter<DosenApiAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemDosenApiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dosen) {
            binding.tvNamaDosen.text  = item.nama
            binding.tvNidnDosen.text  = "NIDN: ${item.nidn}"
            binding.tvProdiDosen.text = item.prodi
            binding.tvEmailDosen.text = item.email

            binding.btnHapusDosen.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDosenApiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Dosen>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}