package com.example.testtask.data.api.response

import com.google.gson.annotations.SerializedName

class PaginationResponse(
    @SerializedName("offset") val offset: Int? = null,
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("count") val count: Int? = null
)
