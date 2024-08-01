package supermarket.model

import java.util.*

class ProductNotExistingInCatalogException(product: Product) :
    RuntimeException("Product ${product.name} does not exist in the catalog")


val APPLE = Product("apples", ProductUnit.Kilo)
val BANANA = Product("bananas", ProductUnit.Kilo)

class CatalogFixture : SupermarketCatalog {
    private val products = HashMap<String, Product>()
    private val prices = HashMap<String, Double>()

    override fun addProduct(product: Product, price: Double) {
        this.products[product.name] = product
        this.prices[product.name] = price
    }

    override fun getUnitPrice(p: Product): Double {
        return this.prices[p.name] ?: throw ProductNotExistingInCatalogException(p)
    }
}
