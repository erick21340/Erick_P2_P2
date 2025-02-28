package edu.ucne.erick_p2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.erick_p2_p2.presentation.navigation.NavigationNavHost

import edu.ucne.erick_p2_p2.ui.theme.Erick_P2_P2Theme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Erick_P2_P2Theme {
                val navHost = rememberNavController()
                NavigationNavHost(
                    navHostController = navHost
                )
            }
        }
    }
}

