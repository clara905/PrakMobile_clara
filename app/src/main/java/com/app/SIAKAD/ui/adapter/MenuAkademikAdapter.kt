package com.app.SIAKAD.ui.adapter

// Letakkan file ini di: app/src/main/java/com/app/akademikapp/ui/adapter/MenuAkademikAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.SIAKAD.data.model.MenuAkademik
import com.app.SIAKAD.databinding.ItemMenuAkademikListBinding

class MenuAkademikAdapter(
    private val items: List<MenuAkademik>,
    private val onItemClick: (MenuAkademik) -> Unit
) : RecyclerView.Adapter<MenuAkademikAdapter.MenuAkademikViewHolder>() {
    inner class MenuAkademikViewHolder(
        private val binding: ItemMenuAkademikListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        // Fungsi bind() menghubungkan data dengan komponen UI
        fun bind(item: MenuAkademik) {
            binding.tvMenuTitle.text = item.title
            binding.tvMenuDescription.text = item.description

            // Set listener klik pada seluruh card item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    // Dipanggil saat RecyclerView membutuhkan ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAkademikViewHolder {
        val binding = ItemMenuAkademikListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuAkademikViewHolder(binding)
    }

    // Dipanggil untuk mengikat data ke ViewHolder pada posisi tertentu
    override fun onBindViewHolder(holder: MenuAkademikViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Mengembalikan jumlah total item dalam list
    override fun getItemCount(): Int = items.size
}