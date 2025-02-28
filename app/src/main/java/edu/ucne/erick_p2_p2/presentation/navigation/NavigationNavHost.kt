package edu.ucne.erick_p2_p2.presentation.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.erick_p2_p2.presentation.componet.NavigationDrawer
import edu.ucne.erick_p2_p2.presentation.componet.home.HomeScreen
import edu.ucne.erick_p2_p2.presentation.screen.deposito.CreateDepositoScreen

import edu.ucne.erick_p2_p2.presentation.screen.deposito.DeleteDepositosScreen
import edu.ucne.erick_p2_p2.presentation.screen.deposito.EditDepositosScreen
import edu.ucne.erick_p2_p2.presentation.screen.deposito.IndexDepositosScreen


@Composable
fun NavigationNavHost(
    navHostController: NavHostController
) {

    val isDrawerVisible = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Screen.HomeScreen) {


            // Home
            composable<Screen.HomeScreen> {
                HomeScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value }
                )
            }
            // Depositos

            composable<Screen.IndexDepositosScreen> {
                IndexDepositosScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                    onCreateDeposito = {
                        navHostController.navigate(Screen.CreateDepositosScreen)
                    },
                    onEditDeposito = {
                        navHostController.navigate(Screen.EditDepositosScreen(it))
                    },
                    onDeleteDeposito = {
                        navHostController.navigate(Screen.DeleteDepositosScreen(it))
                    }
                )
            }

            composable<Screen.CreateDepositosScreen> {
                CreateDepositoScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToDeposito = {
                        navHostController.navigate(Screen.IndexDepositosScreen)
                    }
                )
            }

            composable<Screen.EditDepositosScreen> { backStackEntry ->
                val IdDeposito = backStackEntry.arguments?.getInt(" IdDeposito")
                if (IdDeposito != null) {

                    EditDepositosScreen(
                        idDeposito = IdDeposito ,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToDeposito = {
                            navHostController.navigate(Screen.IndexDepositosScreen)
                        }
                    )
                }
            }


            composable<Screen.DeleteDepositosScreen> { backStackEntry ->
                val IdDeposito = backStackEntry.arguments?.getInt("IdDeposito")
                if (IdDeposito != null) {

                    DeleteDepositosScreen(
                        IdDeposito = IdDeposito,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToDeposito = {
                            navHostController.navigate(Screen.IndexDepositosScreen)
                        }
                    )
                }
            }



        }

        NavigationDrawer(
            isVisible = isDrawerVisible.value,
            onItemClick = { itemTitle ->
                when (itemTitle) {
                    "Inicio" -> navHostController.navigate(Screen.HomeScreen)
                    "Deposito" -> navHostController.navigate(Screen.IndexDepositosScreen)

                }
                isDrawerVisible.value = false
            },
            onClose = {
                isDrawerVisible.value = false
            }
        )
    }
}
