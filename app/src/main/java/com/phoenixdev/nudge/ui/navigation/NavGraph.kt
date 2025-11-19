package com.phoenixdev.nudge.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phoenixdev.nudge.ui.components.AddTaskDialog
import com.phoenixdev.nudge.ui.screens.auth.LoginScreen
import com.phoenixdev.nudge.ui.screens.auth.RegisterScreen
import com.phoenixdev.nudge.ui.screens.home.HomeScreen
import com.phoenixdev.nudge.ui.viewmodel.AuthViewModel
import com.phoenixdev.nudge.ui.viewmodel.TaskViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val authState by authViewModel.authState.collectAsState()

            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                authState = authState,
                onLogin = { email, password ->
                    authViewModel.login(email, password)
                },
                onClearError = {
                    authViewModel.clearError()
                }
            )
        }

        composable(Screen.Register.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val authState by authViewModel.authState.collectAsState()

            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                authState = authState,
                onRegister = { email, password, confirmPassword, name ->
                    authViewModel.register(email, password, confirmPassword, name)
                },
                onClearError = {
                    authViewModel.clearError()
                }
            )
        }

        composable(Screen.Home.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val taskViewModel: TaskViewModel = hiltViewModel()
            
            val tasks by taskViewModel.tasks.collectAsState()
            val statistics by taskViewModel.statistics.collectAsState()
            val currentFilter by taskViewModel.currentFilter.collectAsState()
            val searchQuery by taskViewModel.searchQuery.collectAsState()
            
            var showAddTaskDialog by remember { mutableStateOf(false) }

            HomeScreen(
                tasks = tasks,
                statistics = statistics,
                currentFilter = currentFilter,
                searchQuery = searchQuery,
                onSearchQueryChange = { taskViewModel.setSearchQuery(it) },
                onFilterChange = { taskViewModel.setFilter(it) },
                onTaskClick = { /* TODO: Navigate to task detail */ },
                onTaskComplete = { taskViewModel.toggleTaskCompletion(it) },
                onTaskPin = { taskViewModel.toggleTaskPin(it) },
                onAddTask = { showAddTaskDialog = true },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )

            if (showAddTaskDialog) {
                AddTaskDialog(
                    onDismiss = { showAddTaskDialog = false },
                    onAddTask = { title, description, priority, color ->
                        taskViewModel.addTask(
                            title = title,
                            description = description,
                            priority = priority,
                            color = color
                        )
                    }
                )
            }
        }

        composable(Screen.Profile.route) {
            // Simplified profile screen - just a back button for now
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Profile") },
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Profile Screen - Coming Soon")
                }
            }
        }
    }
}
