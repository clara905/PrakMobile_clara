package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.SIAKAD.R
import com.app.SIAKAD.data.model.MenuAkademik
import com.app.SIAKAD.databinding.FragmentMahasiswaBinding
import com.app.SIAKAD.ui.adapter.MenuAkademikCardAdapter
import com.app.SIAKAD.ui.adapter.MenuAkademikGridAdapter
import com.app.SIAKAD.ui.adapter.MenuAkademikListAdapter
import com.app.SIAKAD.utils.MenuLayoutMode

class MahasiswaFragment : Fragment(R.layout.fragment_mahasiswa) {

    private var _binding: FragmentMahasiswaBinding? = null
    private val binding get() = _binding!!

    private val menuList = listOf(
        MenuAkademik(
            title = "Profil Mahasiswa",
            description = "Identitas dan data akademik dasar mahasiswa.",
            iconResId = android.R.drawable.ic_menu_myplaces
        ),
        MenuAkademik(
            title = "Jadwal Kuliah",
            description = "Daftar jadwal perkuliahan yang sedang aktif.",
            iconResId = android.R.drawable.ic_menu_agenda
        ),
        MenuAkademik(
            title = "Nilai Akademik",
            description = "Hasil studi dan nilai setiap mata kuliah.",
            iconResId = android.R.drawable.ic_menu_info_details
        ),
        MenuAkademik(
            title = "KRS",
            description = "Kelola mata kuliah yang diambil semester ini.",
            iconResId = android.R.drawable.ic_menu_edit
        ),
        MenuAkademik(
            title = "Presensi",
            description = "Riwayat kehadiran kegiatan perkuliahan.",
            iconResId = android.R.drawable.ic_menu_recent_history
        ),
        MenuAkademik(
            title = "Tagihan",
            description = "Rincian pembayaran dan status tagihan akademik.",
            iconResId = android.R.drawable.ic_menu_view
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMahasiswaBinding.bind(view)

        setupLayoutModeSelector()
        applyLayoutMode(MenuLayoutMode.LIST)
        setupActions()
    }

    private fun setupLayoutModeSelector() {
        binding.rgLayoutMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbList -> applyLayoutMode(MenuLayoutMode.LIST)
                R.id.rbGrid -> applyLayoutMode(MenuLayoutMode.GRID)
                R.id.rbCard -> applyLayoutMode(MenuLayoutMode.CARD)
            }
        }
    }

    private fun applyLayoutMode(mode: MenuLayoutMode) {
        when (mode) {
            MenuLayoutMode.LIST -> {
                binding.rvMenuAkademik.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMenuAkademik.adapter =
                    MenuAkademikListAdapter(menuList) { showSelectedMenu(it) }
            }
            MenuLayoutMode.GRID -> {
                binding.rvMenuAkademik.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvMenuAkademik.adapter =
                    MenuAkademikGridAdapter(menuList) { showSelectedMenu(it) }
            }
            MenuLayoutMode.CARD -> {
                binding.rvMenuAkademik.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMenuAkademik.adapter =
                    MenuAkademikCardAdapter(menuList) { showSelectedMenu(it) }
            }
        }
        binding.rvMenuAkademik.setHasFixedSize(true)
    }

    private fun showSelectedMenu(menu: MenuAkademik) {
        Toast.makeText(
            requireContext(),
            "Anda memilih menu: ${menu.title}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupActions() {
        binding.btnKembaliHomeMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMenuAkademik.adapter = null
        _binding = null
    }
}