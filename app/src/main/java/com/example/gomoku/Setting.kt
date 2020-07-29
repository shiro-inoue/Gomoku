package com.example.gomoku

import java.io.Serializable

class Setting (
    var roadbed: Int,
    var forbiddenHand33: Boolean,
    var forbiddenHand44: Boolean,
    var forbiddenHandChohren: Boolean
): Serializable
