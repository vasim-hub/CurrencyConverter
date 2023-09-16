package com.feature.currencyconverter.ui.currency

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.currencyconverter.core.shared.numberFormat

class NumberFormatTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        return text.text.toDoubleOrNull()?.let {
            val formattedAmount = numberFormat.format(it)

            val numberOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return formattedAmount.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }

            TransformedText(
                AnnotatedString(formattedAmount), numberOffsetTranslator
            )
        } ?: TransformedText(
            AnnotatedString(text.text), OffsetMapping.Identity
        )
    }
}
