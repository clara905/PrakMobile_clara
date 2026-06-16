package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.SIAKAD.MainActivity
import com.app.SIAKAD.R
import com.app.SIAKAD.data.model.ApiResponse
import com.app.SIAKAD.data.model.Mahasiswa
import com.app.SIAKAD.data.remote.ApiClient
import com.app.SIAKAD.databinding.FragmentDataMahasiswaApiBinding
import com.app.SIAKAD.ui.adapter.MahasiswaApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataMahasiswaApiFragment : Fragment(R.layout.fragment_data_mahasiswa_api) {

    private var _binding: FragmentDataMahasiswaApiBinding? = null
    private val binding get() = _binding!!
    private lateinit var mahasiswaAdapter: MahasiswaApiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDataMahasiswaApiBinding.bind(view)

        setupRecyclerView()
        setupActions()
        loadMahasiswa()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) loadMahasiswa()
    }

    private fun setupRecyclerView() {
        mahasiswaAdapter = MahasiswaApiAdapter(
            mutableListOf(),
            onEditClick = { mahasiswa ->
                val fragment = FormMahasiswaApiFragment.newInstance(mahasiswa)
                (activity as? MainActivity)?.replaceFragment(fragment)
            },
            onDeleteClick = { mahasiswa ->
                deleteMahasiswa(mahasiswa.id)
            }
        )

        binding.rvMahasiswa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mahasiswaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupActions() {
        binding.btnTambahMahasiswa.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(FormMahasiswaApiFragment.newInstance(null))
        }
        binding.btnKembaliDataMahasiswa.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun loadMahasiswa() {
        binding.progressBarMahasiswa.isVisible = true
        binding.rvMahasiswa.isVisible          = false
        binding.tvEmptyMahasiswa.isVisible     = false

        ApiClient.apiService.getMahasiswa().enqueue(object : Callback<ApiResponse<List<Mahasiswa>>> {

            override fun onResponse(
                call: Call<ApiResponse<List<Mahasiswa>>>,
                response: Response<ApiResponse<List<Mahasiswa>>>
            ) {
                binding.progressBarMahasiswa.isVisible = false
                if (response.isSuccessful) {
                    val data = response.body()?.data ?: emptyList()
                    mahasiswaAdapter.updateData(data)
                    binding.tvEmptyMahasiswa.isVisible = data.isEmpty()
                    binding.rvMahasiswa.isVisible      = data.isNotEmpty()
                } else {
                    showToast("Gagal mengambil data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Mahasiswa>>>, t: Throwable) {
                if (!isAdded || _binding == null) return
                binding.progressBarMahasiswa.isVisible = false
                showToast("Error koneksi: ${t.message}")
            }
        })
    }

    private fun deleteMahasiswa(id: Int) {
        ApiClient.apiService.deleteMahasiswa(id).enqueue(object : Callback<ApiResponse<Any>> {

            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    showToast("Data berhasil dihapus")
                    loadMahasiswa()
                } else {
                    showToast("Gagal menghapus data")
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                if (!isAdded) return
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMahasiswa.adapter = null
        _binding = null
    }
}