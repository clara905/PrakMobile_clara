package com.app.SIAKAD.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.SIAKAD.data.model.Mahasiswa
import com.app.SIAKAD.databinding.ItemMahasiswaBinding

class MahasiswaAdapter(
    private val items: MutableList<Mahasiswa>,
    private val onDeleteClick: (Mahasiswa) -> Unit
) : RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemMahasiswaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Mahasiswa) {
            binding.tvNamaMahasiswa.text = item.nama
            binding.tvNimMahasiswa.text = "NIM: ${item.nim}"
            binding.tvDetailMahasiswa.text = "${item.prodi} - Semester ${item.semester}"
            binding.tvEmailMahasiswa.text = item.email
            binding.btnHapusMahasiswa.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMahasiswaBinding.inflate(
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

    fun updateData(newItems: List<Mahasiswa>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}