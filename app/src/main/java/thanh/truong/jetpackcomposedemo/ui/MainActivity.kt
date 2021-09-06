package thanh.truong.jetpackcomposedemo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import thanh.truong.jetpackcomposedemo.ui.navigation.DemoNavHost
import thanh.truong.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun MyApp() {
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        DemoNavHost(navController = navController, modifier = Modifier.fillMaxWidth())
    }
}

