package com.example.testtask.data.api.response

import com.google.gson.annotations.SerializedName

class ImagesResponse(
    @SerializedName("downsized") val original: OriginalResponse? = null,
) {
    class OriginalResponse(
        @SerializedName("url") val url: String? = null
    )
}
