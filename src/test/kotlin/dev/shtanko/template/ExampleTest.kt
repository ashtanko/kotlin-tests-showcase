/*
 * MIT License

 * Copyright (c) 2025 Oleksii Shtanko

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

package dev.shtanko.template

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExampleTest {

    private val calculator = Calculator()

    @Test
    fun `When adding 1 and 3 then answer is 4`() {
        assertEquals(4, calculator.add(1, 3))
    }

    @Test
    fun `The square of a number should be equal to that number multiplied in itself`() {
        assertAll(
            { assertEquals(1, calculator.square(1)) },
            { assertEquals(4, calculator.square(2)) },
            { assertEquals(9, calculator.square(3)) },
        )
    }

    @Test
    fun `Divide test`() {
        assertAll(
            { assertEquals(2.0, calculator.divide(4, 2)) },
            { assertEquals(1.0, calculator.divide(1, 1)) },
            { assertEquals(1.5, calculator.divide(3, 2)) },
        )
    }

    @Test
    fun `Dividing by zero should throw the DivideByZeroException`() {
        val exception = assertThrows(DivideByZeroException::class.java) {
            calculator.divide(5, 0)
        }
        assertEquals(5, exception.numerator)
    }

    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        assertTrue(list::isEmpty)
    }

    @TestFactory
    fun `Squares test`() = listOf(
        DynamicTest.dynamicTest("when I calculate 1^2 then I get 1") {
            assertEquals(1, calculator.square(1))
        },
        DynamicTest.dynamicTest("when I calculate 2^2 then I get 4") {
            assertEquals(4, calculator.square(2))
        },
        DynamicTest.dynamicTest("when I calculate 3^2 then I get 9") {
            assertEquals(9, calculator.square(3))
        },
    )

    @TestFactory
    fun `Squares map test`() = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25,
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
            assertEquals(expected, calculator.square(input))
        }
    }

    @ParameterizedTest
    @CsvSource(
        "1, 1",
        "2, 4",
        "3, 9",
    )
    fun `Squares test`(input: Int, expected: Int) {
        assertEquals(expected, input * input)
    }

    @Tags(Tag("slow"), Tag("logarithms"))
    @Test
    fun `Log to base 2 of 8 should be equal to 3`() {
        assertEquals(3.0, calculator.log(2, 8))
    }
}
