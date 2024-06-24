package com.functional.mom

import com.functional.mom.data.local.entities.ProductEntity
import com.functional.mom.data.remote.response.Address
import com.functional.mom.data.remote.response.LookFor
import com.functional.mom.data.remote.response.Results
import com.functional.mom.data.remote.response.Seller
import com.functional.mom.repository.models.ResultsModel

const val PERRO = "Perro"
const val ID = "12345"
const val tHUMBNAIL = "https://bankimages.com/imagen.png"
const val PRICE = "5000"
const val NICKNAME = "Mercado libre"
const val ADDRESS = "cll 4 # 28 - 141"
const val CITY = "Zipaquira"

fun createLooFor() = LookFor(
    query = PERRO,
    results = listOf(createResults(), createResults())
)

fun createListResults() = listOf<Results>(createResults(), createResults())
fun createResults() = Results(
    id = ID,
    title = PERRO,
    thumbnail = tHUMBNAIL,
    price = PRICE,
    seller = createSeller(),
    address = createAddress()
)

fun createSeller() = Seller(
    nickname = NICKNAME
)

fun createAddress() = Address(
    address = ADDRESS,
    city_name = CITY
)

fun createListProductEntity() = listOf<ProductEntity>(createProductEntity(), createProductEntity())

fun createProductEntity() = ProductEntity(
    id = 0,
    title = PERRO,
    thumbnail = tHUMBNAIL,
    price = PRICE,
    nickname = NICKNAME,
    address = ADDRESS,
    city = CITY
)

fun createListResultsModel() = listOf<ResultsModel>(createResultsModel(), createResultsModel())

fun createResultsModel() = ResultsModel(
    title = PERRO,
    thumbnail = tHUMBNAIL,
    price = PRICE,
    nickname = NICKNAME,
    address = ADDRESS,
    city_name = CITY
)
