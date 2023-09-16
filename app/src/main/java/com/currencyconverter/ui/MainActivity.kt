package com.currencyconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.currencyconverter.core.shared.datastore.CurrencyConverterAppDataStore
import com.currencyconverter.core.ui.CurrencyConverterTheme
import com.currencyconverter.ui.navigation.CurrencyAppNavigation
import com.currencyconverter.ui.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var currencyConverterAppDataStore: CurrencyConverterAppDataStore
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val appNavigation = CurrencyAppNavigation(navController)

            CurrencyConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainNavigation(
                        navController = navController,
                        appNavigation = appNavigation,
                    )
                }
            }
        }
    }
}
