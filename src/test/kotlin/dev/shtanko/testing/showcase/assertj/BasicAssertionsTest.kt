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

package dev.shtanko.testing.showcase.assertj

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasicAssertionsTest {

    // region Equality Assertions
    @Test
    fun `isEqualTo example test`() {
        assertThat(2 + 2).isEqualTo(4)
    }

    @Test
    fun `isNotEqualTo example test`() {
        assertThat(2 + 2).isNotEqualTo(5)
    }
    // endregion

    // region Null/Non-null Assertions
    @Test
    fun `isNull example test`() {
        val nullableString: String? = null
        assertThat(nullableString).isNull()
    }

    @Test
    fun `isNotNull example test`() {
        val nullableString: String? = "1"
        assertThat(nullableString).isNotNull()
    }
    // endregion

    // region Boolean Assertions
    @Test
    fun `isTrue example test`() {
        assertThat(true).isTrue()
    }

    @Test
    fun `isFalse example test`() {
        assertThat(false).isFalse()
    }
    // endregion

    // region Collection Assertions
    @Test
    fun `isEmpty example test`() {
        assertThat(emptyList<String>()).isEmpty()
    }

    @Test
    fun `isNotEmpty example test`() {
        assertThat(listOf(1, 2, 3)).isNotEmpty()
    }

    @Test
    fun `contains example test`() {
        assertThat(listOf(1, 2, 3)).contains(2)
    }

    @Test
    fun `doesNotContain example test`() {
        assertThat(listOf(1, 2, 3)).doesNotContain(4)
    }

    @Test
    fun `hasSize example test`() {
        assertThat(listOf(1, 2, 3)).hasSize(3)
    }
    // endregion

    // region String Assertions

    @Test
    fun `string isEqualTo example test`() {
        assertThat("Kotlin").isEqualTo("Kotlin")
    }

    @Test
    fun `string contains example test`() {
        assertThat("Kotlin is awesome").contains("awesome")
    }

    @Test
    fun `string startsWith example test`() {
        assertThat("Kotlin").startsWith("Kot")
    }

    @Test
    fun `string endsWith example test`() {
        assertThat("Kotlin").endsWith("lin")
    }

    @Test
    fun `string matches example test`() {
        assertThat("Kotlin").matches("K.*n")
    }
    // endregion

    // region Number Assertions
    @Test
    fun `isGreaterThan example test`() {
        assertThat(5).isGreaterThan(3)
    }

    @Test
    fun `isLessThan example test`() {
        assertThat(2).isLessThan(5)
    }

    @Test
    fun `isBetween example test`() {
        assertThat(5).isBetween(3, 7)
    }
    // endregion

    // region Object Assertions
    @Test
    fun `isInstanceOf example test`() {
        assertThat("Hello").isInstanceOf(String::class.java)
    }

    @Test
    fun `isExactlyInstanceOf example test`() {
        assertThat("Hello").isExactlyInstanceOf(String::class.java)
    }
    // endregion
}
