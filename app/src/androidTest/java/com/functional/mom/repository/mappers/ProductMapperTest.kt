package com.functional.mom.repository.mappers

import com.functional.mom.createListProductEntity
import com.functional.mom.createListResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

/***
 * Test class of [ProductMapper]
 */
@ExperimentalCoroutinesApi
class ProductMapperTest {

    private val productMapper: ProductMapper = ProductMapper

    @Test
    fun `given-a-list-of-listresults-when-transform-returns-a-list-of-listproductentity`() {
        // GIVEN
        val listResults = createListResults()

        // WHEN
        val result = ProductMapper.productResponseToProductEntity(listResults)

        // VERIFY
        Assert.assertEquals(listResults[0].title, result[0].title)
        Assert.assertEquals(listResults[0].thumbnail, result[0].thumbnail)
        Assert.assertEquals(listResults[0].price, result[0].price)
        Assert.assertEquals(listResults[0].seller!!.nickname, result[0].nickname)
        Assert.assertEquals(listResults[0].address!!.address, result[0].address)
        Assert.assertEquals(listResults[0].address!!.city_name, result[0].city)
    }

    @Test
    fun `given-a-list-of-listproductentity-when-transformed-returns-a-list-of-listresultsmodel`() {
        // GIVEN
        val listProductsEntity = createListProductEntity()

        // WHEN
        val result = ProductMapper.productEntityToProductModel(listProductsEntity)

        // VERIFY
        Assert.assertEquals(listProductsEntity[0].title, result[0].title)
        Assert.assertEquals(listProductsEntity[0].thumbnail, result[0].thumbnail)
        Assert.assertEquals(listProductsEntity[0].price, result[0].price)
        Assert.assertEquals(listProductsEntity[0].nickname, result[0].nickname)
        Assert.assertEquals(listProductsEntity[0].address, result[0].address)
        Assert.assertEquals(listProductsEntity[0].city, result[0].city_name)
    }
}
