package edu.ucne.erick_p2_p2.presentation.screen.deposito


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditDepositosScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    idDeposito: Int?,
    onDrawerToggle: () -> Unit,
    goToDeposito: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = idDeposito) {
        if (idDeposito != null) {
            viewModel.getById(idDeposito)
        }
    }

    BodyEditDeposito(
        uiState = uiState,
        onDrawerToggle = onDrawerToggle,
        onConceptoChange = viewModel::onConceptoChange,
        onMontoChange = viewModel::onMontoChange,
        onFechaChange = viewModel::onFechaChange,
        goToDeposito = goToDeposito,
        update = viewModel::update
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyEditDeposito(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    onConceptoChange: (String) -> Unit,
    onMontoChange: (Double) -> Unit,
    onFechaChange: (String) -> Unit,
    goToDeposito: () -> Unit,
    update: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Depósito") },
                navigationIcon = {
                    IconButton(onClick = onDrawerToggle) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = uiState.fecha,
                onValueChange = onFechaChange,
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.concepto,
                onValueChange = onConceptoChange,
                label = { Text("Concepto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = if (uiState.monto == 0.0) "" else uiState.monto.toString(),
                onValueChange = { newValue ->
                    val formattedValue = newValue.replace(",", ".")
                    val doubleValue = formattedValue.toDoubleOrNull()

                    if (doubleValue != null || formattedValue.isEmpty()) {
                        onMontoChange(doubleValue ?: 0.0)
                    }
                },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    update()
                    goToDeposito()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.concepto.isNotEmpty() &&
                        uiState.fecha.isNotEmpty()
            ) {
                Text("Actualizar")
            }

            uiState.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
