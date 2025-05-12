package com.example.myanimelist.data.remote

data class TopGenericResponse<T> (
    val data: List<T>
)