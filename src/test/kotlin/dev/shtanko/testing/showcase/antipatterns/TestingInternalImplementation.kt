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

import io.mockk.Runs
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private data class Order(val id: Int)

private interface OrderRepository {
    fun save(order: Order)

    fun getAllOrders(): List<Order>
}

private interface OrderService {
    fun placeOrder(order: Order)

    fun getOrders(): List<Order>
}

// Fake repository for testing
private class InMemoryOrderRepository : OrderRepository {
    private val orders = mutableListOf<Order>()

    override fun save(order: Order) {
        orders.add(order)
    }

    override fun getAllOrders(): List<Order> = orders.toList()
}

private class OrderServiceImpl(private val repository: OrderRepository) : OrderService {
    override fun placeOrder(order: Order) {
        repository.save(order) // Business logic
    }

    override fun getOrders(): List<Order> {
        return repository.getAllOrders()
    }
}

// Why Is This a Bad Approach?
// Problem: The test breaks when refactoring internal details.
class OrderServiceBadTest {
    @Test
    fun `should call repository save method`() {
        val repository = mockk<OrderRepository>()
        val orderService = OrderServiceImpl(repository)

        every { repository.save(any()) } just Runs

        orderService.placeOrder(Order(1))

        coVerify { repository.save(any()) } // Breaks if the implementation changes
    }
}

// Why Is This a Good Approach?
// * No mock abuse → Uses a simple in-memory repository instead of fragile mock verifications.
// * Encapsulation → We test behavior, not how OrderService works internally.
// * Easier refactoring → If OrderService changes its implementation, the test won’t break unnecessarily.
class OrderServiceGoodTest {
    private val repository = InMemoryOrderRepository()
    private val service = OrderServiceImpl(repository)

    @Test
    fun `should successfully place an order`() {
        val order = Order(1)

        service.placeOrder(order)

        assertTrue(repository.getAllOrders().contains(order)) // Verifying outcome, not implementation details
    }
}
