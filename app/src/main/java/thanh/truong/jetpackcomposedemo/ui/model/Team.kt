package thanh.truong.jetpackcomposedemo.ui.model

import androidx.annotation.DrawableRes

data class Team(
    val homeTeam: String,
    var homeScore: Int,
    @DrawableRes val homeLogo: Int,
    val guestTeam: String,
    var guestScore: Int,
    @DrawableRes val guestLogo: Int
)