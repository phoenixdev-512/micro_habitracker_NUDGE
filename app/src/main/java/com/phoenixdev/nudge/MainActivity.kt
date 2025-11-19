package com.phoenixdev.nudge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.phoenixdev.nudge.ui.navigation.NavGraph
import com.phoenixdev.nudge.ui.navigation.Screen
import com.phoenixdev.nudge.ui.theme.NudgeTheme
import com.phoenixdev.nudge.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Install splash screen
        installSplashScreen()
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        setContent {
            NudgeTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val authState by authViewModel.authState.collectAsState()
                
                // Determine start destination based on authentication state
                val startDestination = if (authState.isAuthenticated) {
                    Screen.Home.route
                } else {
                    Screen.Login.route
                }
                
                NavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}
