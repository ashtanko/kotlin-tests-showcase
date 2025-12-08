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

package dev.shtanko.testing.showcase.libs.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

// Data model
data class User(val name: String)

// API interface
private interface UserApi {
    suspend fun getUser(id: String): User
}

// Service that uses the API
private class UserService(private val api: UserApi) {
    suspend fun load(id: String): User {
        return api.getUser(id)
    }
}

// Test
class MockKExample : FunSpec(
    {

        val api = mockk<UserApi>()
        val service = UserService(api)

        test("loads user") {
            coEvery { api.getUser("1") } returns User("Alex")

            val user = service.load("1")

            user.name shouldBe "Alex"
            coVerify { api.getUser("1") }
        }
    },
)
