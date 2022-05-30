package com.example.testtask.data.api.response

import com.google.gson.annotations.SerializedName

class GifsResponse(
    @SerializedName("data") val gifsData: List<GifDataResponse>,
    @SerializedName("pagination") val pagination: PaginationResponse,
    @SerializedName("meta") val meta: MetaResponse
)
