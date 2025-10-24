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

package dev.shtanko.testing.showcase.antipatterns

import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private class Counter {
    private var count = 0

    fun increment() {
        count++
    }

    fun decrement() {
        if (count > 0) count--
    }

    fun getCount(): Int = count
}

private interface UserService {
    fun getUser(id: Int): User?
}

private class UserController(val userService: UserService) {
    fun getUser(id: Int): User? {
        return userService.getUser(id) // Delegates to the service
    }
}

class TestingWrongFunctionality {
    @Test
    fun `should check if string is empty`() {
        val str = ""
        assertTrue(str.isEmpty()) // This tests Kotlin, not your code!
    }

    @Test
    fun `testing third-party libraries instead of business logic`() {
        val id1 = UUID.randomUUID().toString()
        val id2 = UUID.randomUUID().toString()

        assertNotEquals(id1, id2) // ❌ This tests the UUID library, not our own logic
    }

    @Test
    fun `testing internal state instead of output`() {
        val counter = Counter()
        counter.increment()

        val field = counter.javaClass.getDeclaredField("count")
        field.isAccessible = true
        val count = field.get(counter) as Int

        assertEquals(1, count) // ❌ Accesses private state instead of public behavior
    }

    @Test
    fun `testing configuration instead of business logic`() {
        val userService = mockk<UserService>()
        val controller = UserController(userService)

        assertNotNull(controller.userService) // ❌ This just tests that DI works, not business logic
    }
}
