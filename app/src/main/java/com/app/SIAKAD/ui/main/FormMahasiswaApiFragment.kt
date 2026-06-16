package com.app.SIAKAD.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.SIAKAD.R
import com.app.SIAKAD.data.model.ApiResponse
import com.app.SIAKAD.data.model.Mahasiswa
import com.app.SIAKAD.data.remote.ApiClient
import com.app.SIAKAD.databinding.FragmentFormMahasiswaApiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormMahasiswaApiFragment : Fragment(R.layout.fragment_form_mahasiswa_api) {

    private var _binding: FragmentFormMahasiswaApiBinding? = null
    private val binding get() = _binding!!

    // Data mahasiswa untuk mode edit (null = mode tambah)
    private var mahasiswaEdit: Mahasiswa? = null

    companion object {
        private const val ARG_ID       = "arg_id"
        private const val ARG_NIM      = "arg_nim"
        private const val ARG_NAMA     = "arg_nama"
        private const val ARG_PRODI    = "arg_prodi"
        private const val ARG_SEMESTER = "arg_semester"
        private const val ARG_EMAIL    = "arg_email"

        fun newInstance(mahasiswa: Mahasiswa?): FormMahasiswaApiFragment {
            val fragment = FormMahasiswaApiFragment()
            if (mahasiswa != null) {
                fragment.arguments = Bundle().apply {
                    putInt(ARG_ID, mahasiswa.id)
                    putString(ARG_NIM,      mahasiswa.nim)
                    putString(ARG_NAMA,     mahasiswa.nama)
                    putString(ARG_PRODI,    mahasiswa.prodi)
                    putInt(ARG_SEMESTER,    mahasiswa.semester)
                    putString(ARG_EMAIL,    mahasiswa.email)
                }
            }
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFormMahasiswaApiBinding.bind(view)

        // Cek apakah ada argumen (mode edit)
        arguments?.let { args ->
            mahasiswaEdit = Mahasiswa(
                id       = args.getInt(ARG_ID),
                nim      = args.getString(ARG_NIM, ""),
                nama     = args.getString(ARG_NAMA, ""),
                prodi    = args.getString(ARG_PRODI, ""),
                semester = args.getInt(ARG_SEMESTER),
                email    = args.getString(ARG_EMAIL, "")
            )
        }

        setupForm()
        setupActions()
    }

    private fun setupForm() {
        if (mahasiswaEdit != null) {
            // Mode edit: isi form dengan data yang ada
            binding.tvJudulForm.text = "Edit Mahasiswa"
            binding.edtNim.setText(mahasiswaEdit!!.nim)
            binding.edtNama.setText(mahasiswaEdit!!.nama)
            binding.edtProdi.setText(mahasiswaEdit!!.prodi)
            binding.edtSemester.setText(mahasiswaEdit!!.semester.toString())
            binding.edtEmail.setText(mahasiswaEdit!!.email)
        } else {
            // Mode tambah: form kosong
            binding.tvJudulForm.text = "Tambah Mahasiswa"
        }
    }

    private fun setupActions() {
        binding.btnSimpan.setOnClickListener {
            if (mahasiswaEdit != null) updateMahasiswa() else tambahMahasiswa()
        }
        binding.btnKembali.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun getInputData(): Mahasiswa? {
        val nim          = binding.edtNim.text.toString().trim()
        val nama         = binding.edtNama.text.toString().trim()
        val prodi        = binding.edtProdi.text.toString().trim()
        val semesterText = binding.edtSemester.text.toString().trim()
        val email        = binding.edtEmail.text.toString().trim()

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || semesterText.isEmpty() || email.isEmpty()) {
            showToast("Semua field wajib diisi")
            return null
        }

        val semester = semesterText.toIntOrNull()
        if (semester == null || semester <= 0) {
            showToast("Semester tidak valid")
            return null
        }

        return Mahasiswa(
            id       = mahasiswaEdit?.id ?: 0,
            nim      = nim,
            nama     = nama,
            prodi    = prodi,
            semester = semester,
            email    = email
        )
    }

    private fun tambahMahasiswa() {
        val mahasiswa = getInputData() ?: return

        ApiClient.apiService.tambahMahasiswa(mahasiswa).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    showToast("Mahasiswa berhasil ditambahkan")
                    parentFragmentManager.popBackStack()
                } else {
                    showToast("Gagal menambah mahasiswa. NIM mungkin sudah ada.")
                }
            }
            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                showToast("Error koneksi: ${t.message}")
            }
        })
    }

    private fun updateMahasiswa() {
        val mahasiswa = getInputData() ?: return
        val id        = mahasiswaEdit!!.id

        ApiClient.apiService.updateMahasiswa(id, mahasiswa).enqueue(object : Callback<ApiResponse<Any>> {
            override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    showToast("Data mahasiswa berhasil diupdate")
                    parentFragmentManager.popBackStack()
                } else {
                    showToast("Gagal mengupdate data")
                }
            }
            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                showToast("Error koneksi: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}