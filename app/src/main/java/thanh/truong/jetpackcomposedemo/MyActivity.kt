package thanh.truong.jetpackcomposedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my).apply {
            findViewById<ComposeView>(R.id.vCompose).setContent {
                MaterialTheme {
                    MyMultipleItemList(
                        itemViewStates = listOf(
                            ItemTypeOne("Jetpack compose"),
                            ItemTypeOne("Learn once, write everywhere!"),
                            ItemTypeTwo("Tân Phạm", "Đùng một cái là xong"),
                            ItemTypeTwo("Nam Heo", "Hơi béo"),
                            ItemTypeTwo("Nam béo", "Hơi bê")
                        )
                    )
                }
            }
        }
    }
}