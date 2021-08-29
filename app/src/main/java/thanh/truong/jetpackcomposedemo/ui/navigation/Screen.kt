package thanh.truong.jetpackcomposedemo.ui.navigation

enum class Screen {
    List,
    Detail;

    companion object {
        fun fromRoute(route: String?): Screen =
            when (route?.substringBefore("/")) {
                List.name -> List
                Detail.name -> Detail
                null -> List
                else -> throw IllegalArgumentException("Route $route does not exist")
            }
    }
}
