package com.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.currencyconverter.core.ui.PrimaryColor
import com.currencyconverter.core.ui.Typography
import com.currencyconverter.feature.splash.R
import kotlinx.coroutines.delay

const val SPLASH_SCREEN_TIME = 3000L

@Composable
fun SplashScreen(
    onNavigateNextScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(SPLASH_SCREEN_TIME)
        onNavigateNextScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.splash_image),
            contentDescription = stringResource(R.string.splash_image)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.splash_title_currency_converter),
            style = Typography.bodyLarge.copy(
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )
    }
}
