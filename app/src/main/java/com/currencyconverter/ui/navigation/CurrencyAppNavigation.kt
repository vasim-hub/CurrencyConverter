package com.currencyconverter.ui.navigation

import androidx.navigation.NavHostController

class CurrencyAppNavigation(navController: NavHostController) {

    val navigateToCurrencyScreen: () -> Unit = {
        navController.navigate(NavigationRouteName.ROUTE_CURRENCY) {
            popUpTo(NavigationRouteName.ROUTE_SPLASH)
        }
    }

}
