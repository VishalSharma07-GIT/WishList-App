package com.example.mywishlistapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mywishlistapp.ui.theme.HomeView


import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mywishlistapp.ui.theme.MyWishListAppTheme


import androidx.lifecycle.viewmodel.compose.viewModel // Required for viewModel()
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWishListAppTheme {
                val navController = rememberNavController()
                val viewModel: WishViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "login_screen"
                    ) {
                        // 1️⃣ Login Screen
                        composable("login_screen") {
                            LoginScreen(navController)
                        }

                        // 2️⃣ Home Screen
                        composable(
                            route = "home_screen/{userName}",
                            arguments = listOf(navArgument("userName") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val userName = backStackEntry.arguments?.getString("userName") ?: "User"
                            HomeView(navController, viewModel, userName)
                        }

                        // 3️⃣ Add/Edit Screen
                        composable(
                            route = "add_screen/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("id") ?: 0L
                            AddEditDetailView(id, viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}
