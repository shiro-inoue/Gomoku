package com.example.gomoku

/**
 * 判定状態
 * WIN_BLACK    黒勝ち
 * WIN_WHITE    白勝ち
 * DRAW         引き分け
 * CONTINUE     勝負継続
 */
enum class JudgeState {
    WIN_BLACK, WIN_WHITE, DRAW, CONTINUE
}

/**
 * 連続チェックの方向
 * TOP_BOTTOM       上下
 * LEFT_RIGHT       左右
 * DIAGONAL_RT_LB   右上から左下
 * DIAGONAL_LT_RB   左上から右下
 */
enum class Direction {
    TOP_BOTTOM, LEFT_RIGHT, DIAGONAL_RT_LB, DIAGONAL_LT_RB
}

class Judge {
    private var boardSize = 13

    /**
     * 初期化関数
     * 第一弾（7月は）何もしない
     */
    fun init() {}

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

        //Intから列挙型の変換がうまくいけば、すっきりするはず。調査時間が確保できていないので、現状はコメントアウト
/*        for( i in 0 until 4) {
            Direction[] values = Direction.values()
            var dir: Direction = values[i]
            if( countStraightStone( board, posX, posY, checkCnt, isBlack, dir) == true) {
                return true
            }
        }*/

        if( countStraightStone( board, posX, posY, checkCnt, isBlack, Direction.TOP_BOTTOM) == true) {
            return true
        }
        if( countStraightStone( board, posX, posY, checkCnt, isBlack, Direction.LEFT_RIGHT) == true) {
            return true
        }
        if( countStraightStone( board, posX, posY, checkCnt, isBlack, Direction.DIAGONAL_RT_LB) == true) {
            return true
        }
        if( countStraightStone( board, posX, posY, checkCnt, isBlack, Direction.DIAGONAL_LT_RB) == true) {
            return true
        }
        return false
    }
}
