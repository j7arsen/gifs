package com.example.testtask.data.api.utils

object RequestParams {

    private const val API_KEY = "api_key"
    private const val SEARCH_QUERY = "q"
    private const val LIMIT = "limit"
    private const val OFFSET = "offset"
    private const val RATING = "rating"
    private const val LANG = "lang"

    private const val EN = "en"

    /**
     * @param apiKey GIPHY API Key
     * @param searchQuery search query term or phrase
     * @param limit the maximum number of objects to return
     * @param offset specifies the starting position of the results
     * @param rating filters results by specified rating
     *
     * @return request parameters for search gifs request
     */
    fun getGifsParams(
        apiKey: String,
        searchQuery: String = "",
        limit: Int,
        offset: Int,
        rating: String = "g"
    ) = linkedMapOf(
        API_KEY to apiKey,
        SEARCH_QUERY to searchQuery,
        LIMIT to limit.toString(),
        OFFSET to offset.toString(),
        RATING to rating,
        LANG to EN
    )
}
