package dojo.supermarket.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import supermarket.model.*

class SupermarketTest {
    @Test
    fun `When no discounts is given for a single product, then the unit-price should be the total-price`() {
        val catalog = FakeCatalog()
        val apples = Product("apples", ProductUnit.Kilo)
        catalog.addProduct(apples, 1.99)

        val cart = ShoppingCart()
        cart.addItemQuantity(apples, 1.0)
        val teller = Teller(catalog)

        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(receipt.totalPrice, 1.99)
    }
}
