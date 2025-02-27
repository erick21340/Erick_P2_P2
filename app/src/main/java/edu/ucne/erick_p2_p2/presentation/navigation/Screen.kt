package edu.ucne.erick_p2_p2.presentation.navigation
import kotlinx.serialization.Serializable


sealed class  Screen{
    @Serializable
    data object IndexDepositoScreen : Screen()
    @Serializable
    data object CreateDepositoScreen : Screen()
    @Serializable
    data class EditDepositoScreen(val DepositoId: Int?) : Screen()
    @Serializable
    data class DeleteDepositoScreen(val DepositoId: Int?) : Screen()
}

