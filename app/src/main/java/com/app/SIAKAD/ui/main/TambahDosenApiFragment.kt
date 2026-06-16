package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.SIAKAD.R
import com.app.SIAKAD.data.model.ApiResponse
import com.app.SIAKAD.data.model.Dosen
import com.app.SIAKAD.data.remote.ApiClient
import com.app.SIAKAD.databinding.FragmentTambahDosenApiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahDosenApiFragment : Fragment(R.layout.fragment_tambah_dosen_api) {

    private var _binding: FragmentTambahDosenApiBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTambahDosenApiBinding.bind(view)

        binding.btnSimpanDosen.setOnClickListener { saveDosen() }
        binding.btnKembaliDosen.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun saveDosen() {
        val nidn  = binding.edtNidn.text.toString().trim()
        val nama  = binding.edtNamaDosen.text.toString().trim()
        val prodi = binding.edtProdiDosen.text.toString().trim()
        val email = binding.edtEmailDosen.text.toString().trim()

        if (nidn.isEmpty() || nama.isEmpty() || prodi.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val dosen = Dosen(nidn = nidn, nama = nama, prodi = prodi, email = email)

        ApiClient.apiService.tambahDosen(dosen).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(requireContext(), "Dosen berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Gagal. NIDN mungkin sudah ada.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                if (!isAdded) return
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}