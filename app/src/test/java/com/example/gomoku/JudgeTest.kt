package com.example.gomoku

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.TableRow
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O]) // 動作対象バージョンの指定ができます
class JudgeTest {

    private var mBoard: MutableList<MutableList<Stone>>? = null
    private var mStoneCounter = 0 // 手数

    //9路、19路に変更してテストする場合は、ここを書き換える
    private val MAX_ROW = 13 // 最大 '行' 数(横方向)
    private val MAX_COL = 13 // 最大 '列' 数(縦方向)

    private var mJudge: Judge = Judge()


    @Before
    fun setUp() {
        if(mBoard == null) {
            initBorad()
        } else {
            resetBorad()
        }
        //これを基本セットとする。これ以外を使いたい場合は、各テストケース内で設定すること
        val setting = Setting(MAX_ROW, false, false, true)
        mJudge.init(setting)
    }

    fun initBorad() {
        var btn: Button
        var stone: Stone
        val context = ApplicationProvider.getApplicationContext<Context>()

        mBoard = MutableList(13, { mutableListOf<Stone>() })

        // 基盤の初期化
        for (row in 0 until MAX_ROW) {
            for (col in 0 until MAX_COL) {
                // ボタン生成
                btn = Button(context)
                stone = Stone(StoneState.EMPTY, btn)
                mBoard?.get(row)?.add(stone)
            }
        }

        mStoneCounter = 0

    }

    fun resetBorad() {
        var stone: Stone

        // 基盤のリセット
        for(row in 0 until MAX_ROW) {
            for(col in 0 until MAX_COL) {
                stone = mBoard?.get(row)?.get(col) as Stone
                stone.state = StoneState.EMPTY
            }
        }

        mStoneCounter = 0
    }

    fun setStone(posX: Int, posY: Int, state: StoneState): JudgeState {

        var stone = mBoard?.get(posY)?.get(posX) as Stone
        stone.state = state
        mStoneCounter++
        return mJudge.judgeWinLoss(mBoard, mStoneCounter, posX, posY)

    }

    @Test
    @Throws(Exception::class)
    fun test01_Horizontal01_Win_Black() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(0,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(1,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(2,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(2,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(3,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(3,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(4,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK);

    }

    @Test
    @Throws(Exception::class)
    fun test01_Horizontal02_Win_White() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(0,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(1,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(2,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(2,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(3,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(3,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(5,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白5
        res = setStone(4,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE);
    }

    @Test
    @Throws(Exception::class)
    fun test02_vertical01_Win_Black() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(1,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(0,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(1,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(1,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK);
    }

    @Test
    @Throws(Exception::class)
    fun test02_vertical02_Win_White() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(1,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(0,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(1,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(1,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(0,5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白5
        res = setStone(1,4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE);

    }

    @Test
    @Throws(Exception::class)
    fun test03_Diagonal01_Win_Black() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(0,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(1,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(2,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(2,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(3,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(3,4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(4,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK);

    }

    @Test
    @Throws(Exception::class)
    fun test03_Diagonal02_Win_White() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        // 黒1 白0
        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒1 白1
        res = setStone(0,5, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白1
        res = setStone(1,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒2 白2
        res = setStone(1,4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白2
        res = setStone(2,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒3 白3
        res = setStone(2,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白3
        res = setStone(3,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒4 白4
        res = setStone(3,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白4
        res = setStone(3,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        // 黒5 白5
        res = setStone(4,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE);

    }

    //test04は、チェックリスト順にテストを実施する
    /**
    * 黒番で5目勝ち
    * 縦
    * 最後の着手を5目の端で行う
    * 上端で行う
    */
    @Test
    @Throws(Exception::class)
    fun test04_Vertical_Win_Black() {
        var res:JudgeState

        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE);

        //白石は適当な位置に置くだけで、興味ないので石数だけあわせる
        mStoneCounter++

        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK)
    }

    /**
     * 黒番で5目勝ち
     * 横
     * 最後の着手を5目の端で行う
     * 左端で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Horizontal_Win_Black() {
        var res:JudgeState

        res = setStone(4,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(1,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(2,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(3,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK)
    }

    /**
     * 黒番で5目勝ち
     * 右上斜め
     * 最後の着手を5目の端で行う
     * 右上隅で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Diagonal01_Win_Black() {
        var res:JudgeState

        res = setStone(MAX_COL - 5,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 4,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 3,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 2,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 1,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK)
    }

    /**
     * 黒番で5目勝ち
     * 左上斜め
     * 最後の着手を5目の端で行う
     * 右下隅で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Diagonal02_Win_Black() {
        var res:JudgeState
        var posX = 0
        var posY = 0

        res = setStone(MAX_COL - 5,MAX_ROW - 5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 4,MAX_ROW - 4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 3,MAX_ROW - 3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 2,MAX_ROW - 2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 1,MAX_ROW - 1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK)
    }

    /**
     * 白番で5目勝ち
     * 縦
     * 最後の着手を5目の端以外で行う
     * 下端で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Vertical_Win_White() {
        var res:JudgeState

        //黒石の位置に興味はないので、石数だけあわせる
        mStoneCounter++

        res = setStone(0,MAX_ROW - 5, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,MAX_ROW - 4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,MAX_ROW - 3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,MAX_ROW - 1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,MAX_ROW - 2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE)
    }

    /**
     * 白番で5目勝ち
     * 横
     * 最後の着手を5目の端以外で行う
     * 右端で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Horizontal_Win_White() {
        var res:JudgeState

        mStoneCounter++

        res = setStone(MAX_COL - 1,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 2,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 3,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 4,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(MAX_COL - 5,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE);
    }

    /**
     * 白番で5目勝ち
     * 右上斜め
     * 最後の着手を5目の端以外で行う
     * 左下端で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Diagonal01_Win_White() {
        var res:JudgeState

        mStoneCounter++

        res = setStone(4,MAX_ROW - 5, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(3,MAX_ROW - 4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(2,MAX_ROW - 3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,MAX_ROW - 1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(1,MAX_ROW - 2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE)
    }

    /**
     * 白番で5目勝ち
     * 右上斜め
     * 最後の着手を5目の端以外で行う
     * 左下端で行う
     */
    @Test
    @Throws(Exception::class)
    fun test04_Diagonal02_Win_While() {
        var res:JudgeState

        mStoneCounter++

        res = setStone(0,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(1,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(2,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(4,4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(3,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE)
    }

    /**
     * 黒番で6目反則負け
     */
    @Test
    @Throws(Exception::class)
    fun test04_Straight6_Loss_Black() {
        var res:JudgeState

        res = setStone(0,5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE)
    }

    /**
     * 白番は6目反則負けとならない
     */
    @Test
    @Throws(Exception::class)
    fun test04_Straight6_Win_White() {
        var res:JudgeState

        mStoneCounter++

        res = setStone(0,5, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,4, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,3, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_WHITE)
    }
    /**
     * 6連禁反則ルールオフなので、黒番勝ち
     */
    @Test
    @Throws(Exception::class)
    fun test04_Straight6_Win_Black_At_RuleOff() {
        var res:JudgeState

        //6連禁反則ルールオフ
        val setting = Setting(MAX_ROW, false, false, false)
        mJudge.init(setting)

        res = setStone(0,5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,1, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.WIN_BLACK)
    }

    /**
     * 引き分け
     */
    @Test
    @Throws(Exception::class)
    fun test04_Draw() {
        var res:JudgeState = JudgeState.CONTINUE

        for( y in 0 until MAX_ROW) {
            for( x in 0 until MAX_COL) {
                if( mStoneCounter % 2 == 0) {
                    res = setStone(0, 0, StoneState.BLACK)
                }
                else {
                    res = setStone(0, 0, StoneState.WHITE)
                }
            }

        }
        assertEquals("failure - Judge　res ", res, JudgeState.DRAW)
    }

    /**
     * 1目、間をあけた時に誤って連続と判断しない
     */
    @Test
    @Throws(Exception::class)
    fun test04_Not_Straight5_01() {
        var res:JudgeState

        res = setStone(0,5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)
    }

    /**
     * 1目、別の色の石がある時に誤って連続と判断しない
     */
    @Test
    @Throws(Exception::class)
    fun test04_Not_Straight5_02() {
        var res:JudgeState

        res = setStone(0,5, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,4, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,3, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        mStoneCounter++

        res = setStone(0,2, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        res = setStone(0,1, StoneState.WHITE)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)

        res = setStone(0,0, StoneState.BLACK)
        assertEquals("failure - Judge　res ", res, JudgeState.CONTINUE)
    }
}

//https://qiita.com/nanoyatsu/items/cc2af0d792fad74afe2d
