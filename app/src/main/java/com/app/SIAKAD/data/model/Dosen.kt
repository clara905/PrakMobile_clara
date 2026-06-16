package com.app.SIAKAD.data.model

import com.google.gson.annotations.SerializedName

data class Dosen(
    @SerializedName("id")    val id: Int = 0,
    @SerializedName("nidn")  val nidn: String,
    @SerializedName("nama")  val nama: String,
    @SerializedName("prodi") val prodi: String,
    @SerializedName("email") val email: String
)