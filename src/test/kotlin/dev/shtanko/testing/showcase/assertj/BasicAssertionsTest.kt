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
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.assertj.core.api.Assertions.assertThatNoException
import org.assertj.core.api.Assertions.assertThatNullPointerException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.entry
import org.assertj.core.api.Assertions.tuple
import org.assertj.core.api.Assertions.within
import org.assertj.core.api.Assertions.withinPercentage
import org.assertj.core.api.Assumptions.assumeThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.assertj.core.data.Index
import org.assertj.core.data.Offset
import org.assertj.core.data.Percentage
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class BasicAssertionsTest {

    // ============================================================
    // 1. BASIC ASSERTIONS
    // ============================================================

    @Test
    fun `basic object assertions`() {
        val actual = "AssertJ"

        assertThat(actual)
            .isNotNull()
            .isEqualTo("AssertJ")
            .isNotEqualTo("JUnit")
            .isSameAs(actual)
            .isInstanceOf(String::class.java)
            .isExactlyInstanceOf(String::class.java)
    }

    @Test
    fun `null and optional assertions`() {
        val nullValue: String? = null
        val nonNullValue: String = "value"

        assertThat(nullValue).isNull()
        assertThat(nonNullValue).isNotNull()

        // Optional assertions
        val emptyOptional = Optional.empty<String>()
        val presentOptional = Optional.of("value")

        assertThat(emptyOptional)
            .isNotPresent()
            .isEmpty

        assertThat(presentOptional)
            .isPresent
            .hasValue("value")
            .get()
            .isEqualTo("value")
    }

    // ============================================================
    // 2. STRING ASSERTIONS
    // ============================================================

    @Test
    fun `string assertions`() {
        val text = "AssertJ is awesome"

        assertThat(text)
            .isNotEmpty()
            .hasSize(18)
            .startsWith("Assert")
            .endsWith("awesome")
            .contains("is")
            .containsIgnoringCase("ASSERTJ")
            .doesNotContain("terrible")
            .containsOnlyOnce("awesome")
            .matches(".*awesome$")
            .doesNotMatch("^\\d+")
    }

    @Test
    fun `string case and whitespace assertions`() {
        assertThat("UPPER").isUpperCase()
        assertThat("lower").isLowerCase()
        assertThat("MixedCase").isMixedCase()

        assertThat("  text  ")
            .isNotBlank()
            .containsWhitespaces()

        assertThat("")
            .isEmpty()
    }

    @Test
    fun `string comparison ignoring cases`() {
        assertThat("AssertJ")
            .isEqualToIgnoringCase("assertj")
            .isEqualToIgnoringWhitespace(" AssertJ ")
            .isEqualToNormalizingWhitespace("AssertJ")
    }

    // ============================================================
    // 3. NUMERIC ASSERTIONS
    // ============================================================

    @Test
    fun `integer assertions`() {
        val number = 42

        assertThat(number)
            .isEqualTo(42)
            .isNotZero()
            .isPositive()
            .isGreaterThan(40)
            .isGreaterThanOrEqualTo(42)
            .isLessThan(50)
            .isLessThanOrEqualTo(42)
            .isBetween(40, 50)
            .isCloseTo(40, within(5))
            .isCloseTo(40, Offset.offset(5))
    }

    @Test
    fun `floating point assertions`() {
        val pi = 3.14159

        assertThat(pi)
            .isCloseTo(3.14, within(0.01))
            .isCloseTo(3.14, Offset.offset(0.01))
            .isCloseTo(3.14, withinPercentage(1.0))
            .isCloseTo(3.14, Percentage.withPercentage(1.0))
            .isNotCloseTo(2.0, within(0.5))
    }

    @Test
    fun `number comparisons`() {
        assertThat(10).isEven()
        assertThat(11).isOdd()
        assertThat(0).isZero()
        assertThat(5).isNotNegative()
        assertThat(-5).isNegative()
    }

    // ============================================================
    // 4. COLLECTION ASSERTIONS
    // ============================================================

    @Test
    fun `list basic assertions`() {
        val list = listOf("apple", "banana", "cherry")

        assertThat(list)
            .isNotEmpty()
            .hasSize(3)
            .contains("apple", "banana")
            .containsOnly("apple", "banana", "cherry")
            .containsExactly("apple", "banana", "cherry")
            .containsExactlyInAnyOrder("cherry", "apple", "banana")
            .doesNotContain("orange")
            .doesNotHaveDuplicates()
    }

    @Test
    fun `list order and position assertions`() {
        val list = listOf("a", "b", "c", "d")

        assertThat(list)
            .startsWith("a", "b")
            .endsWith("c", "d")
            .contains("b", Index.atIndex(1))
            .doesNotContain("e", Index.atIndex(0))
    }

    @Test
    fun `collection filtering and extraction`() {
        val people = listOf(
            Person("Alice", 30),
            Person("Bob", 25),
            Person("Charlie", 35),
        )

        assertThat(people)
            .filteredOn { it.age > 25 }
            .hasSize(2)
            .extracting("name")
            .containsExactly("Alice", "Charlie")

        assertThat(people)
            .extracting({ it.name }, { it.age })
            .contains(tuple("Alice", 30), tuple("Bob", 25))
    }

    @Test
    fun `collection element matching`() {
        val numbers = listOf(1, 2, 3, 4, 5)

        assertThat(numbers)
            .allMatch { it > 0 }
            .anyMatch { it > 4 }
            .noneMatch { it > 10 }
            .allSatisfy { assertThat(it).isPositive() }
    }

    @Test
    fun `set assertions`() {
        val set = setOf(1, 2, 3)

        assertThat(set)
            .hasSize(3)
            .contains(1, 2, 3)
            .doesNotContainNull()
            .doesNotHaveDuplicates()
    }

    // ============================================================
    // 5. MAP ASSERTIONS
    // ============================================================

    @Test
    fun `map assertions`() {
        val map = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")

        assertThat(map)
            .isNotEmpty()
            .hasSize(3)
            .containsKey("key1")
            .containsKeys("key1", "key2")
            .doesNotContainKey("key4")
            .containsValue("value1")
            .containsValues("value1", "value2")
            .doesNotContainValue("value4")
            .containsEntry("key1", "value1")
            .containsOnly(
                entry("key1", "value1"),
                entry("key2", "value2"),
                entry("key3", "value3"),
            )
    }

    @Test
    fun `map advanced assertions`() {
        val map = mapOf("name" to "John", "age" to "30")

        assertThat(map)
            .containsAllEntriesOf(mapOf("name" to "John"))
            .doesNotContainEntry("name", "Jane")
            .extracting("name", "age")
            .containsExactly("John", "30")
    }

    // ============================================================
    // 6. ARRAY ASSERTIONS
    // ============================================================

    @Test
    fun `array assertions`() {
        val array = arrayOf("a", "b", "c")

        assertThat(array)
            .hasSize(3)
            .contains("a", "b")
            .containsExactly("a", "b", "c")
            .doesNotContain("d")
            .startsWith("a")
            .endsWith("c")
    }

    @Test
    fun `primitive array assertions`() {
        val intArray = intArrayOf(1, 2, 3, 4, 5)

        assertThat(intArray)
            .hasSize(5)
            .contains(1, 2, 3)
            .containsExactly(1, 2, 3, 4, 5)
            .doesNotContain(6)
            .isSorted()
    }

    // ============================================================
    // 7. BOOLEAN ASSERTIONS
    // ============================================================

    @Test
    fun `boolean assertions`() {
        assertThat(true)
            .isTrue()
            .isNotEqualTo(false)

        assertThat(false)
            .isFalse()
            .isNotEqualTo(true)
    }

    // ============================================================
    // 8. DATE AND TIME ASSERTIONS
    // ============================================================

    @Test
    fun `date assertions`() {
        val date = LocalDate.of(2025, 10, 29)
        val tomorrow = date.plusDays(1)
        val yesterday = date.minusDays(1)

        assertThat(date)
            .isEqualTo(LocalDate.of(2025, 10, 29))
            .isBefore(tomorrow)
            .isAfter(yesterday)
            .isAfterOrEqualTo(date)
            .isBeforeOrEqualTo(date)
            .isBetween(yesterday, tomorrow)
    }

    @Test
    fun `datetime assertions`() {
        val now = LocalDateTime.of(2025, 10, 29, 12, 0, 0)
        val later = now.plusHours(1)

        assertThat(now)
            .isBefore(later)
            .isCloseTo(now.plusMinutes(1), within(2, ChronoUnit.MINUTES))
            .hasYear(2025)
            .hasMonthValue(10)
            .hasDayOfMonth(29)
            .hasHour(12)
    }

    // ============================================================
    // 9. EXCEPTION ASSERTIONS
    // ============================================================

    @Suppress("ThrowsCount")
    @Test
    fun `exception assertions`() {
        assertThatThrownBy { throw IllegalArgumentException("Invalid argument") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid argument")
            .hasMessageContaining("Invalid")
            .hasNoCause()

        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { throw IllegalArgumentException("Error") }
            .withMessage("Error")
            .withMessageContaining("Error")

        assertThatIllegalArgumentException()
            .isThrownBy { throw IllegalArgumentException("Bad input") }
            .withMessage("Bad input")

        assertThatNullPointerException()
            .isThrownBy { throw NullPointerException("Null value") }
    }

    @Test
    fun `no exception assertions`() {
        assertThatNoException()
            .isThrownBy { "This is safe".length }

        assertThatCode {
            val result = 1 + 1
            assertThat(result).isEqualTo(2)
        }.doesNotThrowAnyException()
    }

    // ============================================================
    // 10. FILE ASSERTIONS
    // ============================================================

    @Test
    fun `file assertions`() {
        val tempFile = File.createTempFile("test", ".txt")
        tempFile.writeText("Hello AssertJ")
        tempFile.deleteOnExit()

        assertThat(tempFile)
            .exists()
            .isFile()
            .canRead()
            .canWrite()
            .hasExtension("txt")
            .hasName(tempFile.name)
            .hasContent("Hello AssertJ")

        val tempDir = createTempDir("test")
        tempDir.deleteOnExit()

        assertThat(tempDir)
            .exists()
            .isDirectory()
            .isEmptyDirectory()
    }

    // ============================================================
    // 11. SOFT ASSERTIONS
    // ============================================================

    @Test
    fun `soft assertions - collect all failures`() {
        val person = Person("Alice", 30)

        SoftAssertions().apply {
            assertThat(person.name).isEqualTo("Alice")
            assertThat(person.age).isEqualTo(30)
            assertThat(person.name).startsWith("A")
            assertThat(person.age).isGreaterThan(25)
        }.assertAll()
    }

    @Test
    fun `soft assertions with lambda`() {
        assertSoftly { softly ->
            softly.assertThat("hello").startsWith("h")
            softly.assertThat("hello").endsWith("o")
            softly.assertThat("hello").contains("ll")
            softly.assertThat("hello").hasSize(5)
        }
    }

    // ============================================================
    // 12. ASSUMPTIONS
    // ============================================================

    @Test
    fun `assumptions - conditional test execution`() {
        val runExpensiveTests = false

        assumeThat(runExpensiveTests).isTrue()

        // This code only runs if assumption passes
        // If assumption fails, test is skipped, not failed
        assertThat(1 + 1).isEqualTo(2)
    }

    // ============================================================
    // 13. CUSTOM ASSERTIONS
    // ============================================================

    @Test
    fun `custom error messages`() {
        assertThat("actual")
            .withFailMessage("Expected value to be 'expected' but was 'actual'")
            .isEqualTo("actual")

        assertThat(42)
            .`as`("Checking if number is the answer")
            .isEqualTo(42)

        assertThat(100)
            .describedAs("The percentage score")
            .isLessThanOrEqualTo(100)
    }

    // ============================================================
    // 14. COMPARISON STRATEGIES
    // ============================================================

    @Test
    fun `recursive comparison`() {
        val person1 = Person("Alice", 30)
        val person2 = Person("Alice", 30)

        assertThat(person1)
            .usingRecursiveComparison()
            .isEqualTo(person2)
    }

    @Test
    fun `recursive comparison with ignored fields`() {
        val address1 = Address("123 Main St", "New York", "10001")
        val address2 = Address("456 Oak Ave", "New York", "10001")

        assertThat(address1)
            .usingRecursiveComparison()
            .ignoringFields("street")
            .isEqualTo(address2)
    }

    // ============================================================
    // 15. FLUENT ASSERTIONS WITH EXTRACTING
    // ============================================================

    @Test
    fun `extracting single property`() {
        val people = listOf(
            Person("Alice", 30),
            Person("Bob", 25),
            Person("Charlie", 35),
        )

        assertThat(people)
            .extracting(Person::name)
            .containsExactly(tuple("Alice"), tuple("Bob"), tuple("Charlie"))

        assertThat(people)
            .extracting(Person::age)
            .contains(tuple(30), tuple(25), tuple(35))
    }

    @Test
    fun `extracting multiple properties`() {
        val people = listOf(
            Person("Alice", 30),
            Person("Bob", 25),
        )

        assertThat(people)
            .extracting("name", "age")
            .containsExactly(
                tuple("Alice", 30),
                tuple("Bob", 25),
            )
    }

    // ============================================================
    // 16. FLATEXTRACTING FOR NESTED COLLECTIONS
    // ============================================================

    @Test
    fun `flat extracting nested collections`() {
        val team1 = Team("Team A", listOf("Alice", "Bob"))
        val team2 = Team("Team B", listOf("Charlie", "David"))
        val teams = listOf(team1, team2)

        assertThat(teams)
            .flatExtracting(Team::members)
            .containsExactly(listOf("Alice", "Bob"), listOf("Charlie", "David"))
    }

    // ============================================================
    // 17. SATISFIES AND ANYMATCH
    // ============================================================

    @Test
    fun `satisfies assertions`() {
        val numbers = listOf(2, 4, 6, 8)

        assertThat(numbers).allSatisfy { number ->
            assertThat(number).isEven()
        }

        assertThat(numbers).anySatisfy { number ->
            assertThat(number).isGreaterThan(5)
        }

        assertThat(numbers).noneSatisfy { number ->
            assertThat(number).isOdd()
        }
    }

    // ============================================================
    // 18. ATOMIC REFERENCES AND OPTIONALS
    // ============================================================

    @Test
    fun `optional assertions comprehensive`() {
        val present = Optional.of("value")
        val empty = Optional.empty<String>()

        assertThat(present)
            .isPresent
            .hasValue("value")
            .contains("value")
            .hasValueSatisfying { value ->
                assertThat(value).startsWith("val")
            }

        assertThat(empty)
            .isEmpty
            .isNotPresent
    }

    // ============================================================
    // 19. ITERABLE ASSERTIONS
    // ============================================================

    @Test
    fun `iterable specific assertions`() {
        val iterable: Iterable<String> = listOf("a", "b", "c")

        assertThat(iterable)
            .hasSize(3)
            .containsAnyOf("a", "z")
            .containsExactlyInAnyOrderElementsOf(listOf("c", "a", "b"))
            .hasSameElementsAs(listOf("a", "b", "c"))
    }

    // ============================================================
    // 20. PREDICATE ASSERTIONS
    // ============================================================

    @Test
    fun `predicate assertions`() {
        val isEven: (Int) -> Boolean = { it % 2 == 0 }

        assertThat(isEven)
            .accepts(2, 4, 6)
            .rejects(1, 3, 5)
    }

    // ============================================================
    // 22. ASSERTJ WITH KOTLIN EXTENSIONS
    // ============================================================

    @Test
    fun `kotlin null safety with assertJ`() {
        val nullableString: String = "Hello"

        assertThat(nullableString)
            .isNotNull()

        // Can chain after isNotNull
        assertThat(nullableString)
            .isNotNull()
            .startsWith("H")
    }

    @Test
    fun `kotlin collection extensions`() {
        val list = listOf(1, 2, 3, 4, 5)

        assertThat(list)
            .hasSize(5)
            .first()
            .isEqualTo(1)

        assertThat(list)
            .last()
            .isEqualTo(5)

        assertThat(list)
            .element(2)
            .isEqualTo(3)
    }

    // ============================================================
    // 23. AS AND DESCRIBED AS FOR BETTER ERROR MESSAGES
    // ============================================================

    @Test
    fun `descriptive assertions`() {
        val age = 25

        assertThat(age)
            .`as`("User's age should be adult")
            .isGreaterThanOrEqualTo(18)

        assertThat("test@example.com")
            .describedAs("Email validation")
            .contains("@")
            .contains(".")
    }

    // ============================================================
    // 24. INSTANCE OF ASSERTIONS WITH CASTING
    // ============================================================

    @Test
    fun `instance of with type casting`() {
        val obj: Any = "Hello World"

        assertThat(obj)
            .isInstanceOf(String::class.java)
            .asInstanceOf(org.assertj.core.api.InstanceOfAssertFactories.STRING)
            .startsWith("Hello")
    }

    // ============================================================
    // 25. ZIPPED ASSERTIONS
    // ============================================================

    @Test
    fun `zipped assertions with satisfies`() {
        val names = listOf("Alice", "Bob", "Charlie")
        val ages = listOf(30, 25, 35)

        assertThat(names).zipSatisfy(ages) { name, age ->
            assertThat(name).isNotEmpty()
            assertThat(age).isPositive()
        }
    }

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
        val nullableString: String = "1"
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

// ============================================================
// HELPER CLASSES FOR TESTS
// ============================================================

data class Person(val name: String, val age: Int)

data class Address(val street: String, val city: String, val zipCode: String)

data class Team(val name: String, val members: List<String>)
