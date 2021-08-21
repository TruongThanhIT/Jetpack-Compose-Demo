package thanh.truong.jetpackcomposedemo.ui.model

data class User(
    val id: String? = null,
    val title: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val email: String? = null,
    val dateOfBirth: String? = null,
    val registerDate: String? = null,
    val phone: String? = null,
    val picture: String? = null,
    val location: Location? = null,
)

data class Location(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val timeZone: String? = null,
)