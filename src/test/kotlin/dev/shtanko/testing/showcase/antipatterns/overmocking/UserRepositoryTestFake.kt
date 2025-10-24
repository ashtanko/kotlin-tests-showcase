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

package dev.shtanko.testing.showcase.antipatterns.overmocking

import dev.shtanko.testing.showcase.overmocking.User
import dev.shtanko.testing.showcase.overmocking.UserRepository
import dev.shtanko.testing.showcase.overmocking.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Correct Approach
 * Use fakes or in-memory implementations when possible to test real logic flow
 */
class UserRepositoryTestFake {
    @Test
    fun `should return user details`() {
        val repo = FakeUserRepository()
        val service = UserService(repo)

        val result = service.getUser(1)

        assertEquals("Alex", result?.name)
    }
}

private class FakeUserRepository : UserRepository {
    private val users = mutableMapOf(1 to User(id = "0", name = "Alex"))
    override fun getUser(id: Int): User? = users[id]
}
