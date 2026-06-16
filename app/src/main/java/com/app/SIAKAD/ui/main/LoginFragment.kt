package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.SIAKAD.MainActivity
import com.app.SIAKAD.R
import com.app.SIAKAD.data.local.MahasiswaDbHelper
import com.app.SIAKAD.data.model.ApiResponse
import com.app.SIAKAD.data.model.Mahasiswa
import com.app.SIAKAD.data.remote.ApiClient
import com.app.SIAKAD.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: MahasiswaDbHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        dbHelper = MahasiswaDbHelper(requireContext())

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi username dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1. Cek apakah Admin
            if (username == "admin" && password == "admin") {
                (activity as? MainActivity)?.replaceFragment(AdminFragment())
                return@setOnClickListener
            }

            // 2. Jika bukan admin, cek ke API sebagai Mahasiswa
            binding.btnLogin.isEnabled = false
            ApiClient.apiService.getMahasiswaByNim(username).enqueue(object : Callback<ApiResponse<Mahasiswa>> {
                override fun onResponse(call: Call<ApiResponse<Mahasiswa>>, response: Response<ApiResponse<Mahasiswa>>) {
                    if (!isAdded) return
                    binding.btnLogin.isEnabled = true

                    val apiResponse = response.body()
                    if (response.isSuccessful && apiResponse?.success == true && apiResponse.data != null) {
                        val mahasiswa = apiResponse.data
                        
                        // Cek password default mahasiswa
                        if (password == "1234") {
                            (activity as? MainActivity)?.replaceFragment(
                                DashboardMahasiswaFragment.newInstance(mahasiswa.id)
                            )
                        } else {
                            Toast.makeText(requireContext(), "Password salah", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Akun tidak ditemukan atau data salah", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Mahasiswa>>, t: Throwable) {
                    if (!isAdded) return
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}