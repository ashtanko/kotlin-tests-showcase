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

package dev.shtanko.testing.showcase.libs.junit

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail

// This annotation is used to configure the lifecycle of test instances for the annotated test class or test interface.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnnotationsTest {

    @BeforeAll
    fun initAll() {
        println("initAll")

        // Example: Load a large dataset or configuration file
    }

    @BeforeEach
    fun init() {
        println("init")
        // Example: myService = MyService() // Create a new instance before each test
    }

    @Test
    fun succeedingTest() {
        assertTrue(true)
    }

    @Test
    fun failingTest() {
        // fail { "a failing test" }
    }

    @Test
    @Disabled("for demonstration purposes")
    fun skippedTest() {
        println("skippedTest")
    }

    @Test
    fun abortedTest() {
        assumeTrue("abc".contains("Z"))
        fail("test should have been aborted")
    }

    @AfterEach
    fun tearDown() {
        println("tearDown")
        // Example: close the database connection
    }

    @AfterAll
    fun tearDownAll() {
        println("tearDownAll")
        // Example: Close the shared connection pool
    }
}
