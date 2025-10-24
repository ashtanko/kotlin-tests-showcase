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

import kotlin.math.ln
import kotlin.math.sqrt

/**
 * A calculator.
 *
 * This class just a documentation example.
 *
 * @constructor Creates an empty calculator.
 */
class Calculator {
    /**
     * Sum [a] and [b].
     * @param a
     * @param b
     * @return sum [a] + [b]
     */
    fun add(a: Int, b: Int) = a + b

    fun divide(a: Int, b: Int): Double = if (b == 0) {
        throw DivideByZeroException(a)
    } else {
        a.toDouble() / b.toDouble()
    }

    fun square(a: Int) = a * a

    fun squareRoot(a: Int) = sqrt(a.toDouble())

    fun log(base: Int, value: Int) = ln(value.toDouble()) / ln(base.toDouble())
}
