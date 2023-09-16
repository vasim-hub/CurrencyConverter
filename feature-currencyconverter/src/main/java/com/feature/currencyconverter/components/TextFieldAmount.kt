package com.feature.currencyconverter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.ui.component.CustomTextField
import com.feature.currencyconverter.R
import com.feature.currencyconverter.ui.currency.NumberFormatTransformation

@Composable
fun TextFieldAmount(
    currentAmount: Int,
    onAmountUpdated: (String) -> Unit
) {
    var amountState by remember {
        mutableStateOf(if (currentAmount == 0) "" else currentAmount.toString())
    }
    val keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
    )
    val amountTitle = stringResource(id = R.string.enter_amount)
    val focusManager = LocalFocusManager.current


    LaunchedEffect(amountState) {
        onAmountUpdated(amountState)
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp)),

        ) {
        CustomTextField(
            value = amountState,
            label = amountTitle,
            visualTransformation = NumberFormatTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            onValueChanged = {
                amountState = it
            }
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { amountState = "" },
            enabled = amountState.isNotEmpty(),
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.clear_icon),
                tint = if (amountState.isEmpty()) Color.Transparent else Color.Black
            )
        }
    }
}
