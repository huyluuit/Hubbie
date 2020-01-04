package com.example.hubbie.entities

data class Control(
    var id: Int
    , var nameDisplay: String
    , var roomId: Int
    , var deviceId: Int
    , var state: Boolean
    , var power: Float
    , var current: Float
)