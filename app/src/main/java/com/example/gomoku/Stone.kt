package com.example.gomoku

import android.widget.Button

enum class StoneState {
    EMPTY, BLACK, WHITE
}

class Stone (var state: StoneState, var btn: Button)
