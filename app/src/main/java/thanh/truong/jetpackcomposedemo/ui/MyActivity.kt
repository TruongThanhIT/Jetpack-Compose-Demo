package thanh.truong.jetpackcomposedemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import thanh.truong.jetpackcomposedemo.R
import thanh.truong.jetpackcomposedemo.ui.model.Location
import thanh.truong.jetpackcomposedemo.ui.model.User

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my).apply {
            findViewById<ComposeView>(R.id.vCompose).setContent {
                MaterialTheme {
                    val user = User(
                        title = "Mr",
                        lastName = "Nguyen",
                        firstName = "Hung",
                        gender = "male",
                        email = "v.hungnpk@cloudhms.net",
                        dateOfBirth = "01/01/1994",
                        phone = "0912303002",
                        location = Location(
                            street = "188 Vo Thi Sau",
                            city = "District 1",
                            state = "Ho Chi Minh city",
                            country = "Vietnam"
                        )
                    )
                    UserDetailInfoCard(
                        user = user, modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}