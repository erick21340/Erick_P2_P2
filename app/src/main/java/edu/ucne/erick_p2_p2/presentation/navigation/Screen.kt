package edu.ucne.erick_p2_p2.presentation.navigation
import kotlinx.serialization.Serializable


sealed class Screen {

    @Serializable
    data object HomeScreen : Screen()
    @Serializable
    data object IndexDepositosScreen : Screen()
    @Serializable
    data object CreateDepositosScreen : Screen()
    @Serializable
    data class EditDepositosScreen(val IdDeposito: Int?) : Screen()
    @Serializable
    data class DeleteDepositosScreen(val IdDeposito: Int?) : Screen()
}

