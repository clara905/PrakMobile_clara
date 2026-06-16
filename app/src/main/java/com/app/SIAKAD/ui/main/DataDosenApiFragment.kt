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
import com.app.SIAKAD.data.model.Dosen
import com.app.SIAKAD.data.remote.ApiClient
import com.app.SIAKAD.databinding.FragmentDataDosenApiBinding
import com.app.SIAKAD.ui.adapter.DosenApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataDosenApiFragment : Fragment(R.layout.fragment_data_dosen_api) {

    private var _binding: FragmentDataDosenApiBinding? = null
    private val binding get() = _binding!!
    private lateinit var dosenAdapter: DosenApiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDataDosenApiBinding.bind(view)

        setupRecyclerView()
        setupActions()
        loadDosen()
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null) loadDosen()
    }

    private fun setupRecyclerView() {
        dosenAdapter = DosenApiAdapter(mutableListOf()) { dosen ->
            deleteDosen(dosen.id)
        }

        binding.rvDosen.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dosenAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupActions() {
        binding.btnTambahDosen.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(TambahDosenApiFragment())
        }
        binding.btnKembaliDosen.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun loadDosen() {
        val currentBinding = _binding ?: return
        currentBinding.progressBarDosen.isVisible = true
        currentBinding.rvDosen.isVisible          = false
        currentBinding.tvEmptyDosen.isVisible     = false

        ApiClient.apiService.getDosen().enqueue(object : Callback<ApiResponse<List<Dosen>>> {

            override fun onResponse(
                call: Call<ApiResponse<List<Dosen>>>,
                response: Response<ApiResponse<List<Dosen>>>
            ) {
                val b = _binding ?: return
                b.progressBarDosen.isVisible = false
                if (response.isSuccessful) {
                    val data = response.body()?.data ?: emptyList()
                    dosenAdapter.updateData(data)
                    b.tvEmptyDosen.isVisible = data.isEmpty()
                    b.rvDosen.isVisible      = data.isNotEmpty()
                } else {
                    showToast("Gagal mengambil data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Dosen>>>, t: Throwable) {
                if (!isAdded || _binding == null) return
                binding.progressBarDosen.isVisible = false
                showToast("Error koneksi: ${t.message}")
            }
        })
    }

    private fun deleteDosen(id: Int) {
        ApiClient.apiService.deleteDosen(id).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
                if (response.isSuccessful) {
                    showToast("Dosen berhasil dihapus")
                    if (_binding != null) loadDosen()
                } else {
                    showToast("Gagal menghapus dosen")
                }
            }
            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                if (!isAdded) return
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(msg: String) {
        if (isAdded) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding?.rvDosen?.adapter = null
        super.onDestroyView()
        _binding = null
    }
}