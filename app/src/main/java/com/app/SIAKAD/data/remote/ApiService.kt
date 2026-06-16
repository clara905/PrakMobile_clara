package com.app.SIAKAD.data.remote

import com.app.SIAKAD.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /* ===== MAHASISWA ===== */

    @GET("mahasiswa")
    fun getMahasiswa(): Call<ApiResponse<List<Mahasiswa>>>

    @GET("mahasiswa/nim/{nim}")
    fun getMahasiswaByNim(
        @Path("nim") nim: String
    ): Call<ApiResponse<Mahasiswa>>

    @POST("mahasiswa")
    fun tambahMahasiswa(
        @Body mahasiswa: Mahasiswa
    ): Call<ApiResponse<Any>>

    @PUT("mahasiswa/{id}")
    fun updateMahasiswa(
        @Path("id") id: Int,
        @Body mahasiswa: Mahasiswa
    ): Call<ApiResponse<Any>>

    @DELETE("mahasiswa/{id}")
    fun deleteMahasiswa(
        @Path("id") id: Int
    ): Call<ApiResponse<Any>>


    /* ===== DOSEN ===== */

    @GET("dosen")
    fun getDosen(): Call<ApiResponse<List<Dosen>>>

    @POST("dosen")
    fun tambahDosen(@Body dosen: Dosen): Call<ApiResponse<Any>>

    @DELETE("dosen/{id}")
    fun deleteDosen(@Path("id") id: Int): Call<ApiResponse<Any>>


    /* ===== MATA KULIAH ===== */

    @GET("matkul")
    fun getMataKuliah(): Call<ApiResponse<List<MataKuliah>>>

    @POST("matkul")
    fun tambahMataKuliah(@Body mataKuliah: MataKuliah): Call<ApiResponse<Any>>

    @DELETE("matkul/{id}")
    fun deleteMataKuliah(@Path("id") id: Int): Call<ApiResponse<Any>>
}