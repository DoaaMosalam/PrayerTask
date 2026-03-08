package com.doaamosalam.prayertask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.doaamosalam.prayertask.ui.screen.PrayerScreen
import com.doaamosalam.prayertask.ui.theme.PrayerTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrayerTaskTheme {


                    PrayerScreen()

            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrayerTaskTheme {
        PrayerScreen(

        )
    }
}