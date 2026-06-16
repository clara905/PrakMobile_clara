package com.app.SIAKAD.data.model

import com.google.gson.annotations.SerializedName

data class MataKuliah(
    @SerializedName("id")       val id: Int = 0,
    @SerializedName("kode")     val kode: String,
    @SerializedName("nama")     val nama: String,
    @SerializedName("sks")      val sks: Int,
    @SerializedName("semester") val semester: Int
)