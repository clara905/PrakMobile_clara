package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.SIAKAD.MainActivity
import com.app.SIAKAD.R
import com.app.SIAKAD.databinding.FragmentAdminBinding

class AdminFragment : Fragment(R.layout.fragment_admin) {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAdminBinding.bind(view)

        binding.btnKelolaMahasiswa.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(DataMahasiswaApiFragment())
        }

        binding.btnKelolaDosen.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(DataDosenApiFragment())
        }

        binding.btnKembaliHomeAdmin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}