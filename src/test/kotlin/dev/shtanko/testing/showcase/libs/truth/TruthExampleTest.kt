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

package dev.shtanko.testing.showcase.libs.truth

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class TruthExampleTest {
    @Test
    fun `should return correct result`() {
        val result = 2 + 2
        assertThat(result).isEqualTo(4)
    }

    @Test
    fun testStrings() {
        val message = "Hello, Truth!"
        assertThat(message).contains("Truth")
    }
}

class CollectionTruthTest {
    @Test
    fun testListContents() {
        val fruits = listOf("apple", "banana", "orange")
        assertThat(fruits).containsExactly("banana", "orange", "apple")
    }

    @Test
    fun testMapContents() {
        val map = mapOf("kotlin" to 2.2, "java" to 21)
        assertThat(map).containsEntry("kotlin", 2.2)
        assertThat(map).doesNotContainKey("scala")
    }
}

class NullableTruthTest {
    @Test
    fun testNullValues() {
        val name: String? = null
        assertThat(name).isNull()
    }

    @Test
    fun testNonNull() {
        val language: String? = "Kotlin"
        assertThat(language).isNotNull()
    }
}
