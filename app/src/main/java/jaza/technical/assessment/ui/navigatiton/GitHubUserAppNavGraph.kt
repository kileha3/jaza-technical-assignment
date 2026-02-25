package jaza.technical.assessment.ui.navigatiton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jaza.technical.assessment.ui.screens.userdetail.UserDetailScreen
import jaza.technical.assessment.ui.screens.userlist.UserListScreen

sealed class Screen(val route: String) {
    object UserList : Screen("user_list")
    object UserDetail : Screen("user_detail/{username}") {
        fun createRoute(username: String) = "user_detail/$username"
    }
}

@Composable
fun GitHubUserAppNavGraph(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.UserList.route,
        modifier = modifier
    ) {
        composable(Screen.UserList.route) {
            UserListScreen(
                onUserClick = { username ->
                    navController.navigate(Screen.UserDetail.createRoute(username))
                },
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }

        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { _ ->
            UserDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}