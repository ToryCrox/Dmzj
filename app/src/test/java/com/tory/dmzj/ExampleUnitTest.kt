package com.tory.dmzj

import org.junit.Test

import org.junit.Assert.*
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun url_test(){
        var url = URL("https://images.dmzj.com/tuijian/222_222/170811shaonvaqqing.jpg")

    }
}
