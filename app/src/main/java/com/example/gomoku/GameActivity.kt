package com.example.gomoku

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

/**
 * 五目並べのゲームアクティビティクラス
 */
class GameActivity : AppCompatActivity() {
    /**
     * Activity起動時に初めにコールされるメソッド
     *
     * @param savedInstanceState アクティビティの以前に保存された状態を含む Bundle オブジェクト
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初期化処理
        mBoard = MutableList(MAX_ROW, {mutableListOf<Stone>()})

        // 基盤の初期化
        initBoard();

        // Settingクラスの受け取り
        val setting = intent.getSerializableExtra("SETTING")

        // Judgeクラスのinitをコール
        mJudge.init(setting as Setting)

    }

    private val MAX_ROW = 13 // 最大 '行' 数(縦方向)
    private val MAX_COL = 13 // 最大 '列' 数(横方向)

    /**　手数　*/
    private var mStoneCounter = 0
    /**　ボードの石情報　*/
    private var mBoard: MutableList<MutableList<Stone>>? = null
    /**　判定クラス　*/
    private val mJudge: Judge = Judge()

    /**
     * 基盤の表示、初期化設定
     */
    private fun initBoard() {

        var tableRow: TableRow
        var btn: Button
        var stone: Stone

        // 端（基盤の目は正方形なので、縦横同じ数とする）
        val edge = MAX_ROW - 1

        // アプリケーションサイズの取得
        val size = getDispSize()
        // 基盤の目のサイズを取得
        val gridWidth: Int = size.x / MAX_COL

        //　アクティビティに xml を配置
        setContentView(R.layout.activity_game)

        //　戻るボタンの設定
        back.setOnClickListener(backListener)

        // リセットボタン
        reset.setOnClickListener(resetListener)

        // テーブルレイアウト
        //val tableLayout :TableLayout  = TableLayout(this);
        // TableLayout を xml から取得
        val tableLayout: TableLayout = findViewById(R.id.displayLinear);
        // バックグラウンド(色)を設定
        tableLayout.setBackgroundColor(Color.WHITE);

        // 基盤の初期化
        for(row in 0 until MAX_ROW) {
            // 行(Row)の生成
            tableRow = TableRow(this)
            for(col in 0 until MAX_COL) {
                // ボタン生成
                btn = Button(this)
                // クリックイベント設定
                btn.setOnClickListener(goListener)
                // ボタンに画像を設定
                initButtonImage(btn, col, row, edge)
                // テーブルロウにボタンを追加(ボタンの大きさは正方形なので、高さ・幅ともに同じ)
                tableRow.addView(btn, gridWidth, gridWidth)
                // 石を生成(初期化)
                stone = Stone(StoneState.EMPTY, btn)
                mBoard?.get(row)?.add(stone)
            }
            // テーブルレイアウトに設定
            tableLayout.addView(tableRow)
        }
    }

    /**
     * 基盤の画像設定（ボタンの初期化）
     */
    private fun initButtonImage(btn: Button, col: Int, row: Int, edge:Int){
        // ボタン
        btn.setEnabled(true);

        // 左上
        if((col == 0) && (row == 0)) {
            btn.background = getDrawable(R.drawable.cell_empty_tl)
        }
        // 右上
        else if((col == edge) && (row == 0)) {
            btn.background = getDrawable(R.drawable.cell_empty_tr)
        }
        // 左下
        else if((col == 0) && (row == edge)) {
            btn.background = getDrawable(R.drawable.cell_empty_bl)
        }
        // 右下
        else if((col == edge) && (row == edge)) {
            btn.background = getDrawable(R.drawable.cell_empty_br)
        }
        // 上端
        else if(row == 0) {
            btn.background = getDrawable(R.drawable.cell_empty_t)
        }
        // 下端
        else if(row == edge) {
            btn.background = getDrawable(R.drawable.cell_empty_b)
        }
        // 左端
        else if(col == 0) {
            btn.background = getDrawable(R.drawable.cell_empty_l)
        }
        // 右端
        else if(col == edge) {
            btn.background = getDrawable(R.drawable.cell_empty_r)
        }
        // 上記以外
        else {
            btn.background = getDrawable(R.drawable.cell_empty)
        }
    }

    /**
     * リセット処理
     */
    private fun resetProcess() {
        val edge = MAX_ROW - 1
        var stone: Stone
        for(row in 0 until MAX_ROW) {
            for(col in 0 until MAX_COL) {
                stone = mBoard?.get(row)?.get(col) as Stone
                // 石の状態を空にする
                stone.state = StoneState.EMPTY
                // ボタンの画像を設定
                initButtonImage(stone.btn, col, row, edge)
            }
        }
        // 初期化
        mStoneCounter = 0
    }

    /**
     * 碁のイベント処理
     */
    private val goListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == null) {
                return
            }

            // 手数をカウント
            mStoneCounter++
            // 石の状態を持つ変数
            var stoneState: StoneState = StoneState.EMPTY
            // ボタンへキャスト
            val btn = v as Button
            // ボタンを押せなくする
            btn.setEnabled(false)

            // 石を置く
            if(mStoneCounter % 2 != 0) {
                // 先手
                btn.background = getDrawable(R.drawable.stone_black)
                stoneState = StoneState.BLACK
            } else {
                //　後手
                btn.background = getDrawable(R.drawable.stone_white)
                stoneState = StoneState.WHITE
            }

            playProcess(btn, stoneState)
        }
    }

    /**
     * 碁を打った後の処理
     */
    private fun playProcess(btn: Button, stoneState: StoneState) {
        var stone: Stone?
        var posX: Int = MAX_ROW
        var posY: Int = MAX_COL
        var res: JudgeState
        var message: String = ""

        // 石を置いた場所の検索と石の状態を設定(黒、色を設定)
        for(row in 0 until MAX_ROW) {
            for(col in 0 until  MAX_COL) {
                stone = mBoard?.get(row)?.get(col) as Stone
                if(stone.btn == btn) {
                    stone.state = stoneState
                    posX = col
                    posY = row
                    break;
                }
            }
        }

        // Judge
        res = mJudge.judgeWinLoss(mBoard, mStoneCounter, posX, posY)
        when(res){
            JudgeState.WIN_BLACK -> {
                message = getString(R.string.msg_win_black)
            }
            JudgeState.WIN_WHITE -> {
                message = getString(R.string.msg_win_white)
            }
            JudgeState.DRAW -> {
                message = getString(R.string.msg_draw)
            }
        }

        // ゲームが終了したら、ダイアログ表示
        if(res != JudgeState.CONTINUE) {
            val dialog = AlertDialog.Builder(this)
                //.setTitle("引き分け") // タイトル
                .setMessage(message) // メッセージ
                .setPositiveButton(getString(R.string.ok_button)) { dialog, which -> // OK
                    resetProcess()
                }
                .create()
            // AlertDialogを表示
            dialog.show()
            return
        }
    }

    /**
     * 戻るボタンのイベント処理
     */
    private val backListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    /**
     * リセットボタンのイベント処理
     */
    private val resetListener = object: View.OnClickListener {
        override fun onClick(v: View?) {
            // リセット処理
            resetProcess()
        }
    }

    /**
     * ！！！仮！！！
     * 画面のサイズ取得
     */
    private fun getDispSize(): Point {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val disp = wm.defaultDisplay
        val size = Point()
        disp.getSize(size)
        return size
    }
}