package dojo.supermarket.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import supermarket.model.*

val APPLE = Product("apples", ProductUnit.Kilo)
val BANANA = Product("bananas", ProductUnit.Kilo)

class SupermarketTest {

    private val catalog = FakeCatalog().apply {
        addProduct(APPLE, 1.99)
        addProduct(BANANA, 2.99)
    }

    @Test
    fun `When no discounts is given for a single product, then the unit-price should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(receipt.totalPrice, 1.99)
    }

    @Test
    fun `When two units of the same product are used without a discount, then the unit-price multiplied by two should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 2.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(receipt.totalPrice, 3.98)
    }

    @Test
    fun `When the same product is used twice without a discount, then the unit-price multiplied by two should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
            addItemQuantity(APPLE, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(receipt.totalPrice, 3.98)
    }


    @Test
    fun `When two products are used without a discount, then the sum of both unit-prices should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
            addItemQuantity(BANANA, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(receipt.totalPrice, 4.98)
    }

    @Test
    fun `When and product is added to the cart, then a useful exception should be thrown`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(Product("unknown-product", ProductUnit.Kilo), 1.0)
        }

        assertThrows<ProductNotExistingInCatalogException> {
            Teller(catalog).checksOutArticlesFrom(cart)
        }
    }
}
