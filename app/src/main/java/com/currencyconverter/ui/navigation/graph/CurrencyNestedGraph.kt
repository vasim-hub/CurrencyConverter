package com.currencyconverter.ui.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.currencyconverter.R
import com.currencyconverter.ui.navigation.NavigationDestination
import com.currencyconverter.ui.navigation.NavigationRouteName
import com.feature.currencyconverter.ui.currency.CurrencyConverterScreen

fun NavGraphBuilder.currencyNestedGraphNavigationFlow() {
    navigation(
        startDestination = NavigationDestination.CURRENCY_SCREEN,
        route = NavigationRouteName.ROUTE_CURRENCY
    ) {
        composable(NavigationDestination.CURRENCY_SCREEN) {
            CurrencyConverterScreen(
                appTitle = stringResource(id = R.string.app_name)
            )
        }
    }
}
