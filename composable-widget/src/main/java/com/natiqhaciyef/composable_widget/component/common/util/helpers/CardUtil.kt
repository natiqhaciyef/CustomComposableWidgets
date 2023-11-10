package com.natiqhaciyef.composable_widget.component.common.util.helpers

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.natiqhaciyef.composable_widget.R
import com.natiqhaciyef.composable_widget.component.common.util.classes.PaymentChoiceModel
import com.natiqhaciyef.composable_widget.component.common.util.classes.PaymentTypes


fun formatOtherCardNumbers(text: String): TransformedText {

    val trimmed = if (text.length >= 16) text.substring(0..15) else text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += " "
    }
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun formatExpirationDate(text: String): TransformedText {

    val trimmed = if (text.length >= 5) text.substring(0..3) else text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 3 == 1) out += "/"
    }
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset < 4) return offset
            if (offset == 4) return offset + 1
            if (offset > 4) return offset
            return 4
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < 4) return offset
            if (offset >= 4) return offset - 1
            return offset
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

object PaymentMethodList {
    val list = mutableListOf(
        PaymentChoiceModel(
            type = PaymentTypes.VISA,
            image = R.drawable.visa,
            isSelected = true
        ),
        PaymentChoiceModel(
            type = PaymentTypes.MASTERCARD,
            image = R.drawable.mastercard,
            isSelected = false
        ),
        PaymentChoiceModel(
            type = PaymentTypes.PAYPAL,
            image = R.drawable.paypal,
            isSelected = false
        ),
    )
}

fun cardTypeToImageFinder(paymentMethod: String): Int {
    return when (paymentMethod.lowercase()) {
        PaymentTypes.VISA.mainName.lowercase() -> PaymentMethodList.list[0].image
        PaymentTypes.MASTERCARD.mainName.lowercase() -> PaymentMethodList.list[1].image
        PaymentTypes.PAYPAL.mainName.lowercase() -> PaymentMethodList.list[2].image
        else -> 0
    }
}

