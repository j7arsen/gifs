package com.example.testtask.data.api.response

import com.google.gson.annotations.SerializedName

class MetaResponse(
    @SerializedName("msg") val msg: String? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("response_id") val responseId: String? = null,

)