package com.albums.albums.data.model

import com.google.gson.annotations.SerializedName

data class AlbumItem(

    @SerializedName("userId")
    val userId: Int? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    )