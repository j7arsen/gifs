package com.example.testtask.data.db

object DatabaseInfo {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "testtask_db"

    const val TABLE_GIFS = "tableGifs"
    const val GIF_ID = "gifId"
    const val GIF_URL = "gifUrl"
    const val GIF_CREATE_DATE = "gifCreateDate"
    const val GIF_TITLE = "gifTitle"

    const val TABLE_GIF_REMOTE_KEYS = "tableGifRemoteKeys"
    const val PREVIOUS_PAGE = "previousPage"
    const val NEXT_PAGE = "nextPage"
    const val LAST_UPDATED = "lastUpdated"
}
