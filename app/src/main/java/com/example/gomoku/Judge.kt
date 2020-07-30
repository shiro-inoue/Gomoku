package com.example.gomoku

/**
 * 判定状態
 */
enum class JudgeState {
    /** 黒勝ち */
    WIN_BLACK,
    /** 白勝ち */
    WIN_WHITE,
    /** 引き分け */
    DRAW,
    /** 勝負継続 */
    CONTINUE
}

/**
 * 連続チェックの方向
 */
enum class Direction(val dirId: Int) {
    /** 上下 */
    TOP_BOTTOM(0),
    /** 左右 */
    LEFT_RIGHT(1),
    /** 右上から左下 */
    DIAGONAL_RT_LB(2),
    /** 左上から右下 */
    DIAGONAL_LT_RB(3);

    companion object {
        fun valueOf(dirId:Int) :Direction {
            //参考サイト
            //https://kwmt27.net/2017/10/19/kotlin-enum/　　　Int→列挙型変換について
            //https://qiita.com/watame/items/87b7923d4f3f59ffb653　　filterの使い方
            val filtered = Direction.values().filter {it.dirId == dirId}.firstOrNull()
            return filtered ?: throw IllegalArgumentException("no such enum object for the dirId: " + dirId)
        }
    }
}

class Judge {
    private var boardSize = 13

    /**
     * 初期化関数
     * 第一弾（7月は）何もしない
     */
    fun init(setting: Setting) {println("***** setting.roadbed = " + setting.roadbed)}

    /**
     * 勝負け判定関数
     * @param board         盤情報
     * @param stoneCounter  石総数
     * @param posX          着手位置
     * @param posY          着手位置
     * @return              判定結果 @see #JudgeState
     */
    fun judgeWinLoss( board: MutableList<MutableList<Stone>>?, stoneCounter: Int, posX: Int, posY: Int): JudgeState {
        //石数なので、1始まり。奇数は黒番
        val isBlack = (stoneCounter % 2 == 1)

        //5つ並んだら、基本は勝ち
        if( checkStraightStone(board, posX, posY, 5, isBlack)) {
            if( isBlack) {
                //6つ並んだら、黒番は反則負け
                if( checkStraightStone(board, posX, posY, 6, isBlack)) {
                    return JudgeState.WIN_WHITE
                }
                return JudgeState.WIN_BLACK
            }
            return JudgeState.WIN_WHITE
        }
        //8月は盤の大きさが変わるので、もらう必要あり
        if( stoneCounter == boardSize * boardSize) {
            return JudgeState.DRAW
        }
        return JudgeState.CONTINUE
    }

    /**
     * 指定方向の石数（連続）をカウントする関数
     * @param board         盤情報
     * @param posX          着手位置
     * @param posY          着手位置
     * @param isBlack       黒番か
     * @param dir           指定方向　@see #Direction
     * @return              連続していればtrueを返却
     */
    private fun countStraightStone( board: MutableList<MutableList<Stone>>, posX: Int, posY: Int, checkCnt: Int, isBlack: Boolean, dir: Direction): Boolean {
        var checkColor = StoneState.EMPTY
        var counter = 0
        var x = posX
        var y = posY
        var addX = 0
        var addY = 0

        //判定する石の色を決定
        if( isBlack) {
            checkColor = StoneState.BLACK
        }
        else {
            checkColor = StoneState.WHITE
        }

        //指定方向に応じた、addX,addYを設定。
        when(dir) {
            Direction.TOP_BOTTOM -> {
                addX = 0
                addY = -1
            }
            Direction.LEFT_RIGHT -> {
                addX = -1
                addY = 0
            }
            Direction.DIAGONAL_RT_LB -> {
                addX = -1
                addY = 1
            }
            Direction.DIAGONAL_LT_RB -> {
                addX = -1
                addY = -1
            }
        }

        //連続性をカウント。上方向（左方向、斜め上方向）・下方向（右方向、斜め下方向）の順にカウントする
        //基準点は石の色がわかっているので、カウント済みとする
        counter = 1
        for( i in 0 until 2) {
            if( i == 1) {
                //チェック方向を逆転
                addX *= -1
                addY *= -1
            }
            //基準点はカウント済みなので、スキップする処理を実施
            x = posX + addX
            y = posY + addY

            while (x >= 0 && y >= 0 && x < boardSize && y < boardSize && counter < checkCnt) {
                if (board[y][x].state == checkColor) {
                    counter++
                } else {
                    break
                }
                y += addY
                x += addX
            }
        }

        if( counter >= checkCnt) {
            return true
        }
        return false
    }

    /**
     * 縦・横・斜めに、石が指定数（連続）あるかのチェック関数
     * @param board         盤情報
     * @param posX          着手位置
     * @param posY          着手位置
     * @param isBlack       黒番か
     * @return              連続していればtrueを返却
     */
    private fun checkStraightStone( board: MutableList<MutableList<Stone>>?, posX: Int, posY: Int, checkCnt: Int, isBlack: Boolean): Boolean {
        if( board == null) {
            return false
        }

        for( i in 0 until 4) {
            if( countStraightStone( board, posX, posY, checkCnt, isBlack, Direction.valueOf(i)) == true) {
                return true
            }
        }

        return false
    }
}
