package com.natiqhaciyef.composable_widget.component.common.util.helpers

fun priceConverter(currency: String): String {
    return when (currency) {
        "USD" -> { "$" }
        "AZN" -> { "Azn" }
        "EURO" -> { "€" }
        "Pound" -> { "£" }
        "CAD" -> { "$ CAD" }
        "TL" -> { "TL" }
        else -> { "Currency not selected" }
    }
}


fun priceValueConverter(price: Double): String{
    return if (price.toInt().toDouble() == price){
        "${price.toInt()}"
    }else{
        "$price"
    }
}