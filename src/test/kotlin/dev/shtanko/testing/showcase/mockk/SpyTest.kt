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

package dev.shtanko.testing.showcase.mockk

import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Spies allow you to mix mocks and real objects.
 */
class SpyTest {

    @Test
    fun `test spy example`() {
        val calculator = spyk(Calculator())

        // Call the real method
        assertEquals(5, calculator.add(2, 3))

        // Stub multiply() method
        every { calculator.multiply(2, 3) } returns 100

        // multiply() will return the stubbed value, but add() remains real
        assertEquals(100, calculator.multiply(2, 3))
        assertEquals(5, calculator.add(2, 3))

        // Verify method calls
        verify { calculator.add(2, 3) }
        verify { calculator.multiply(2, 3) }
    }

    private class Calculator {
        fun add(a: Int, b: Int): Int {
            return a + b
        }

        fun multiply(a: Int, b: Int): Int {
            return a * b
        }
    }
}
