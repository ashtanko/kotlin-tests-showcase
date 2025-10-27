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

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test

class CapturingArgumentsTest {

    private val repository = mockk<UserRepository>()

    @Test
    fun `capturing arguments example test`() {
        val slot = slot<Int>()
        every { repository.updateUser(capture(slot), any()) } just Runs
        updateUser(User(1, "Alice"), repository)

        println("Captured id: ${slot.captured}") // Output: Captured id: 1
        verify { repository.updateUser(1, "Alice") }
    }

    private data class User(val id: Int, val name: String)

    private fun updateUser(user: User, repository: UserRepository) {
        repository.updateUser(user.id, user.name)
    }

    private interface UserRepository {
        fun updateUser(id: Int, name: String)
    }
}
