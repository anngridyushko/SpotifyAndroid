package com.example.spotidroidapp.spotiapi

class GSongs<T> private constructor(
    val tracks: Tracks<T>
)

class Tracks<T> private constructor(
    val items: T
    )