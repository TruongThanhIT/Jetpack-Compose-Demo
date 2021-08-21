package thanh.truong.jetpackcomposedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import thanh.truong.jetpackcomposedemo.R

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my).apply {
            findViewById<ComposeView>(R.id.vCompose).setContent {
                MaterialTheme {

                }
            }
        }
    }
}