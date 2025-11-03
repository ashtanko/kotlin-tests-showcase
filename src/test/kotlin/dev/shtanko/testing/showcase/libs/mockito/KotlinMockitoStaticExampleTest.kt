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

import java.util.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class KotlinMockitoStaticExampleTest {

    private lateinit var repository: Repository
    private val service: Service = mock()

    @BeforeEach
    fun setUp() {
        repository = Repository(service)
    }

    @Test
    fun `WHEN changeNickname THEN send correct params to backend`() = runTest {
        Mockito.mockStatic(UUID::class.java).use { mockedUUID ->
            val expectedNickname = "JohnDoe"
            val uuid = mock<UUID>()
            whenever(uuid.toString()).thenReturn("uuid")
            mockedUUID.`when`<Any> { UUID.randomUUID() }.thenReturn(uuid)

            repository.changeNickname(expectedNickname)

            verify(service).changeNickname(
                nickname = expectedNickname,
                uuid = "uuid",
            )
        }
    }

    private class Repository(private val service: Service) {
        suspend fun changeNickname(newNickname: String) {
            service.changeNickname(newNickname, UUID.randomUUID().toString())
        }
    }

    private class Service {
        fun changeNickname(nickname: String, uuid: String) {
            println("$nickname $uuid")
        }
    }
}
