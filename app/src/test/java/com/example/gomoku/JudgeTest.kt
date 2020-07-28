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


}

//https://qiita.com/nanoyatsu/items/cc2af0d792fad74afe2d
