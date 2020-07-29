package com.example.gomoku

import android.content.Context
import android.os.Build
import android.widget.Button
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O]) // 動作対象バージョンの指定ができます
class StoneTest {

    @Test
    @Throws(Exception::class)
    fun test() {
        // 2. contextを取得する
        val context = ApplicationProvider.getApplicationContext<Context>()
        val btn: Button = Button(context)

        var stone: Stone
        stone = Stone(StoneState.EMPTY, btn)

        assertEquals("failure - stone.btn ", btn, stone.btn);

        assertEquals("failure - stone.State ", StoneState.EMPTY, stone.state);

        stone = Stone(StoneState.BLACK, btn)
        assertEquals("failure - stone.State ", StoneState.BLACK, stone.state);

        stone = Stone(StoneState.WHITE, btn)
        assertEquals("failure - stone.State ", StoneState.WHITE, stone.state);

        stone.state = StoneState.EMPTY
        assertEquals("failure - stone.State ", StoneState.EMPTY, stone.state)

        stone.state = StoneState.BLACK
        assertEquals("failure - stone.State ", StoneState.BLACK, stone.state);

        stone.state = StoneState.WHITE
        assertEquals("failure - stone.State ", StoneState.WHITE, stone.state);
    }
}

//https://qiita.com/nanoyatsu/items/cc2af0d792fad74afe2d
