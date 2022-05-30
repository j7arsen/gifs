package com.example.testtask.data.api.response

import com.google.gson.annotations.SerializedName

class GifDataResponse(
    @SerializedName("type") val type: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("source") val source: String? = null,
    @SerializedName("rating") val rating: String? = null,
    @SerializedName("import_datetime") val importDatetime: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("images") val images: ImagesResponse? = null
)
