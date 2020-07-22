package com.example.gomoku

import java.io.Serializable

class Setting (
    var board: Int,
    var rule33: Boolean,
    var rule44: Boolean
): Serializable