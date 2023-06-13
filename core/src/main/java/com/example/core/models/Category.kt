package com.example.core.models

data class Category(
    override val id: Int,
    val image_url: String,
    val name: String
): Identifiable