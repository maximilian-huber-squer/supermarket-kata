package supermarket.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    @Test
    fun `When a product is added to the cart, then the cart should contain the product`() {
        val cart = ShoppingCart()
        val product = Product("apples", ProductUnit.Kilo)

        cart.addItemQuantity(product, 1.0)

        assertTrue(cart.getItems().any { it.product == product })
    }

    @Test
    fun `When a product is added to the cart, then the cart should contain the right product quantity`() {
        val cart = ShoppingCart()
        val product = Product("apples", ProductUnit.Kilo)

        cart.addItemQuantity(product, 1.0)

        assertEquals(1.0, cart.productQuantities()[product])
    }

    @Test
    fun `When a product is added to the cart twice, then the cart should contain the right product quantity`() {
        val cart = ShoppingCart()
        val product = Product("apples", ProductUnit.Kilo)

        cart.addItemQuantity(product, 1.0)
        cart.addItemQuantity(product, 1.0)

        assertEquals(2.0, cart.productQuantities()[product])
    }
}