package com.example.gomoku

import java.io.Serializable

class Stone(
    var size: Int,
    var rule33: Boolean,
    var rule44: Boolean
) : Serializable
