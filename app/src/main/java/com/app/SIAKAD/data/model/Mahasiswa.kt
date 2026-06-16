package com.app.SIAKAD.data.model

import com.google.gson.annotations.SerializedName

data class Mahasiswa(
    @SerializedName("id")       val id: Int = 0,
    @SerializedName("nim")      val nim: String,
    @SerializedName("nama")     val nama: String,
    @SerializedName("prodi")    val prodi: String,
    @SerializedName("semester") val semester: Int,
    @SerializedName("email")    val email: String
)