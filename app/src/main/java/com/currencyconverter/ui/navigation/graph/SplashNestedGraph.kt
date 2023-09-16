package com.currencyconverter.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.currencyconverter.ui.navigation.NavigationDestination
import com.currencyconverter.ui.navigation.NavigationRouteName
import com.feature.splash.SplashScreen

fun NavGraphBuilder.splashNestedGraphNavigationFlow(
    onNavigateNextScreen: () -> Unit
) {
    navigation(
        startDestination = NavigationDestination.SPLASH_SCREEN,
        route = NavigationRouteName.ROUTE_SPLASH
    ) {
        composable(NavigationDestination.SPLASH_SCREEN) {
            SplashScreen(
                onNavigateNextScreen = onNavigateNextScreen
            )
        }
    }
}
