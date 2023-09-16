
package com.currencyconverter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.currencyconverter.ui.navigation.graph.currencyNestedGraphNavigationFlow
import com.currencyconverter.ui.navigation.graph.splashNestedGraphNavigationFlow

@Composable
fun MainNavigation(
    navController: NavHostController,
    appNavigation: CurrencyAppNavigation
) {

    NavHost(navController = navController, startDestination = NavigationRouteName.ROUTE_SPLASH) {

        splashNestedGraphNavigationFlow(
            onNavigateNextScreen = appNavigation.navigateToCurrencyScreen
        )

        currencyNestedGraphNavigationFlow()
    }
}
