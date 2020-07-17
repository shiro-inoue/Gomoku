package com.example.gomoku

enum class JudgeState {
    WIN_BLACK, WIN_WHITE, DRAW, CONTINUE
}

class Judge {
    //初期化関数。第一弾（7月は）何もしない
    fun init() {}

    //勝負け判定関数
    fun judgeWinLoss( board: List<Stone>, stoneCounter: Int, x: Int, y: Int): JudgeState {
        //石数なので、1始まり。奇数は黒番
        val isBlack = (stoneCounter % 2 == 1)

        //5つ並んだら、基本は勝ち
        if( checkStraightStone(board, x, y, 5)) {
            if( isBlack) {
                //6つ並んだら、黒番は反則負け
                if( checkStraightStone(board, x, y, 6)) {
                    return JudgeState.WIN_WHITE
                }
                return JudgeState.WIN_BLACK
            }
            return JudgeState.WIN_WHITE
        }
        //8月は盤の大きさが変わるので、もらう必要あり
        if( stoneCounter == 13 * 13) {
            return JudgeState.DRAW
        }
        return JudgeState.CONTINUE
    }

    private fun checkStraightStone( board: List<Stone>, x: Int, y: Int, checkCnt: Int): Boolean {
        //いったんすべてfalseとする
        return false
    }
}
