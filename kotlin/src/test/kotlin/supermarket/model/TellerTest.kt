package supermarket.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TellerTest {

    private val catalog = CatalogFixture().apply {
        addProduct(APPLE, 1.99)
        addProduct(BANANA, 2.99)
    }

    @Test
    fun `When no discounts is given for a single product, then the unit-price should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(1.99, receipt.totalPrice)
    }

    @Test
    fun `When two units of the same product are used without a discount, then the unit-price multiplied by two should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 2.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(3.98, receipt.totalPrice)
    }

    @Test
    fun `When the same product is used twice without a discount, then the unit-price multiplied by two should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
            addItemQuantity(APPLE, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(3.98, receipt.totalPrice)
    }


    @Test
    fun `When two products are used without a discount, then the sum of both unit-prices should be the total-price`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 1.0)
            addItemQuantity(BANANA, 1.0)
        }

        val receipt = Teller(catalog).checksOutArticlesFrom(cart)

        assertEquals(4.98, receipt.totalPrice)
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

    @Test
    fun `When product has special offer type three for two and you have three products should apply offer`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 3.0)
        }

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, APPLE, 10.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(3.98, receipt.totalPrice)
    }

    @Test
    fun `When product has special offer type two for amount and the amount is 1, 10 products should be 5`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 10.0)
        }

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, APPLE, 1.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(5.0, receipt.totalPrice)
    }

    @Test
    fun `When product has special offer type ten percent discount, the total price should be reduced by ten percent`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 100.0)
        }

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, APPLE, 1.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(197.01, receipt.totalPrice)
    }

    @Test
    fun `When product has special offer type five for amount, the total price should be the amount of the product times the amount divided by five`() {
        val cart = ShoppingCart().apply {
            addItemQuantity(APPLE, 10.0)
        }

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, APPLE, 5.0)
        val receipt = teller.checksOutArticlesFrom(cart)

        assertEquals(10.0, receipt.totalPrice)
    }
}
