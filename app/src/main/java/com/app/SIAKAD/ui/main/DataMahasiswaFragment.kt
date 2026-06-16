package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.SIAKAD.MainActivity
import com.app.SIAKAD.R
import com.app.SIAKAD.data.local.MahasiswaDbHelper
import com.app.SIAKAD.databinding.FragmentDataMahasiswaBinding
import com.app.SIAKAD.ui.adapter.MahasiswaAdapter

class DataMahasiswaFragment : Fragment(R.layout.fragment_data_mahasiswa) {

    private var _binding: FragmentDataMahasiswaBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: MahasiswaDbHelper
    private lateinit var mahasiswaAdapter: MahasiswaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDataMahasiswaBinding.bind(view)
        dbHelper = MahasiswaDbHelper(requireContext())

        setupRecyclerView()
        setupActions()
        loadMahasiswa()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) loadMahasiswa()
    }

    private fun setupRecyclerView() {
        mahasiswaAdapter = MahasiswaAdapter(mutableListOf()) { mahasiswa ->
            val success = dbHelper.deleteMahasiswa(mahasiswa.id)
            if (success) {
                Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                loadMahasiswa()
            } else {
                Toast.makeText(requireContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvMahasiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mahasiswaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupActions() {
        binding.btnTambahMahasiswa.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(TambahMahasiswaFragment())
        }
        binding.btnKembaliDataMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun loadMahasiswa() {
        val data = dbHelper.getAllMahasiswa()
        mahasiswaAdapter.updateData(data)
        binding.tvEmptyMahasiswa.isVisible = data.isEmpty()
        binding.rvMahasiswa.isVisible = data.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMahasiswa.adapter = null
        _binding = null
    }
}