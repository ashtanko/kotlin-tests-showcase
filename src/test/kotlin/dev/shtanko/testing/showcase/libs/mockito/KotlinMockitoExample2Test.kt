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

package dev.shtanko.testing.showcase.libs.mockito

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class KotlinMockitoExample2Test {
    private val api: Service = mock()
    private val cache: Storage = mock()
    private val repository: UserRepository = UserRepositoryImpl(api, cache)

    @Test
    fun `GIVEN nickname present in storage WHEN getUsername THEN return value from storage AND don't call backend`() =
        runTest {
            val expectedUsername = "oleksii"
            whenever(cache.getUsername()).thenReturn(expectedUsername)
            val username = repository.getUsername()
            assertEquals(expectedUsername, username)
            verify(api, never()).fetchUsername()
        }

    @Test
    fun `GIVEN nickname not present in storage WHEN getNickname THEN return value from backend AND put to storage`() =
        runTest {
            val expectedUsername = "oleksii"
            whenever(api.fetchUsername()).thenReturn(expectedUsername)
            whenever(cache.getUsername()).thenReturn(null)
            val username = repository.getUsername()
            assertEquals(expectedUsername, username)
            verify(api, times(1)).fetchUsername()
            verify(cache, times(1)).saveUsername(expectedUsername)
        }
}

private interface Service {
    suspend fun fetchUsername(): String
}

private interface Storage {
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): String?
}

private interface UserRepository {
    suspend fun getUsername(): String
}

private class UserRepositoryImpl(val api: Service, val cache: Storage) : UserRepository {
    override suspend fun getUsername(): String {
        val usernameFromCache = cache.getUsername()
        return if (usernameFromCache == null) {
            val username = api.fetchUsername()
            cache.saveUsername(username)
            username
        } else {
            usernameFromCache
        }
    }
}
