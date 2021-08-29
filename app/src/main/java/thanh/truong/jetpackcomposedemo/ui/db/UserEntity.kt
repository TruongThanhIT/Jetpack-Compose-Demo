package thanh.truong.jetpackcomposedemo.ui.db

import androidx.annotation.Keep
import androidx.room.Entity
import java.util.*

@Keep
@Entity(
    tableName = "user",
    primaryKeys = ["id"]
)
data class UserEntity(
    var id: String = UUID.randomUUID().toString(),
    var title: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var gender: String? = null,
    var email: String? = null,
    var dateOfBirth: String? = null,
    var registerDate: String? = null,
    var phone: String? = null,
    var picture: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null
)
