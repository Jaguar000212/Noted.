package com.jaguar.noted.navigation

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route: String = "home"
}

object Settings : Destinations {
    override val route: String = "settings"
}
