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

package dev.shtanko.testing.showcase.junit

import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.*
import java.util.function.Supplier
import java.util.stream.IntStream
import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE
import org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL
import org.junit.jupiter.params.provider.FieldSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

class ParameterizedTestsTest {

    // region @ValueSource
    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3])
    fun testWithValueSource(argument: Int) {
        assertTrue(argument in 1..<4)
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = [" ", "   ", "\t", "\n"])
    fun nullEmptyAndBlankStrings(text: String?) {
        assertTrue(text == null || text.trim().isEmpty())
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = [" ", "   ", "\t", "\n"])
    fun nullEmptyAndBlankStringsOneAnnotation(text: String?) {
        assertTrue(text == null || text.trim().isEmpty())
    }
    // endregion

    // region @EnumSource
    @ParameterizedTest
    @EnumSource(ChronoUnit::class)
    fun testWithEnumSource(unit: TemporalUnit?) {
        assertNotNull(unit)
    }

    @ParameterizedTest
    @EnumSource
    fun testWithEnumSourceWithAutoDetection(unit: ChronoUnit?) {
        assertNotNull(unit)
    }

    @ParameterizedTest
    @EnumSource(names = ["DAYS", "HOURS"])
    fun testWithEnumSourceInclude(unit: ChronoUnit?) {
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit))
    }

    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = ["ERAS", "FOREVER"])
    fun testWithEnumSourceExclude(unit: ChronoUnit?) {
        assertFalse(EnumSet.of(ChronoUnit.ERAS, ChronoUnit.FOREVER).contains(unit))
    }

    @ParameterizedTest
    @EnumSource(mode = MATCH_ALL, names = ["^.*DAYS$"])
    fun testWithEnumSourceRegex(unit: ChronoUnit) {
        assertTrue(unit.name.endsWith("DAYS"))
    }
    // endregion

    // region @MethodSource
    @ParameterizedTest
    @MethodSource("stringProvider")
    fun testWithExplicitLocalMethodSource(argument: String) {
        assertNotNull(argument)
    }

    @ParameterizedTest
    @MethodSource
    fun testWithDefaultLocalMethodSource(argument: String) {
        assertNotNull(argument)
    }

    @ParameterizedTest
    @MethodSource("range")
    fun testWithRangeMethodSource(argument: Int) {
        assertNotEquals(9, argument)
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    fun testWithMultiArgMethodSource(str: String, num: Int, list: List<String>) {
        assertEquals(5, str.length)
        assertTrue(num >= 1 && num <= 2)
        assertEquals(2, list.size)
    }
    // endregion

    // region @FieldSource
    @ParameterizedTest
    @FieldSource
    fun arrayOfFruits(fruit: String) { // uses the field name as the source
        assertTrue(arrayOfFruits.contains(fruit))
    }

    @ParameterizedTest
    @FieldSource("listOfFruits")
    fun singleFieldSource(fruit: String) {
        assertTrue(listOfFruits.contains(fruit))
    }

    @ParameterizedTest
    @FieldSource
    fun namedArgumentsSupplier(fruit: String) {
        println(fruit)
    }
    // endregion

    companion object {

        @JvmStatic
        val arrayOfFruits = arrayOf("apple", "banana")

        @JvmStatic
        val listOfFruits = listOf("apple", "banana")

        @JvmStatic
        fun stringProvider(): List<String> {
            return listOf("apple", "banana")
        }

        @JvmStatic
        fun testWithDefaultLocalMethodSource(): List<String> {
            return listOf("apple", "banana")
        }

        @JvmStatic
        fun range(): IntArray {
            return IntStream.range(0, 20).skip(10).toArray()
        }

        @JvmStatic
        fun stringIntAndListProvider(): List<Array<Any>> {
            return listOf(
                arrayOf("apple", 1, listOf("a", "b")),
                arrayOf("lemon", 2, listOf("x", "y")),
            )
        }

        @JvmStatic
        val namedArgumentsSupplier: Supplier<Stream<Arguments>> = Supplier {
            Stream.of(
                arguments("apple"),
                arguments("banana"),
            )
        }
    }
}
