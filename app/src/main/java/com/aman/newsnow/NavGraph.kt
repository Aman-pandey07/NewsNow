package com.aman.newsnow

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable




@Composable
fun MainScreen(navController: NavHostController, newsViewModel: NewsViewModel,modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,    // The controller responsible for navigation
        startDestination = "home",
        modifier = modifier// The starting screen
    ) {
        composable("home") {              // Define the route for the home screen
            HomePage(newsViewModel = newsViewModel, navController = navController)
        }
        composable("detail") {            // Define the route for the detail screen
            val selectedArticle = newsViewModel.selectedArticle.value
            selectedArticle?.let { ArticleDetailScreen(article = it) }
        }
    }
}