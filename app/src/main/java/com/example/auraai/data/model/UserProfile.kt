package com.example.auraai.data.model

data class UserProfile(
    val name: String = "",
    val age: String = "",
    val phone: String = "",
    val traits: List<String> = emptyList()
)
