package thanh.truong.jetpackcomposedemo.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import thanh.truong.jetpackcomposedemo.ui.MainScreen
import thanh.truong.jetpackcomposedemo.ui.UserDetailScreen

val detailName = Screen.Detail.name

@ExperimentalMaterialApi
@Composable
fun DemoNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.name,
        modifier = modifier
    ) {
        // route to list screen
        composable(Screen.List.name) {
            MainScreen(onUserClick = { id ->
                navController.navigate("$detailName/$id")
            })
        }
        // route to detail screen
        composable(
            route = "$detailName/{user-id}",
            arguments = listOf(navArgument("user-id") {
                type = NavType.StringType
            })
        ) {
            // show Detail screen
            UserDetailScreen(userId = it.arguments?.getString("user-id"), onClose = {
                navController.navigateUp()
            })
        }
    }
}