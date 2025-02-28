package edu.ucne.erick_p2_p2.presentation.screen.deposito




import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar


@Composable
fun   CreateDepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    goToDeposito: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BodyCreateDeposito(
        uiState = uiState,
        onDrawerToggle = onDrawerToggle,
        onConceptoChange = viewModel::onConceptoChange,
        onMontoChange = viewModel::onMontoChange,
        onFechaChange = viewModel::onFechaChange,
        goToDeposito = goToDeposito,
        save = viewModel::save
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyCreateDeposito(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    onConceptoChange: (String) -> Unit,
    onMontoChange: (Double) -> Unit,
    onFechaChange: (String) -> Unit,
    goToDeposito: () -> Unit,
    save: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Deposito") },
                navigationIcon = {
                    IconButton(onClick = {
                        onDrawerToggle()
                    }) {
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

            CreateDatePicker(uiState, onFechaChange)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    save()
                    goToDeposito()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled =   uiState.concepto.isNotEmpty() &&
                            uiState.fecha.isNotEmpty()
            ) {
                Text("Guardar")
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




@Composable
fun CreateDatePicker(
    uiState: UiState,
    onFechaChange: (String) -> Unit
) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val selectedDate = uiState.fecha ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { expanded.value = true }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (selectedDate.isEmpty()) "Seleccionar fecha" else selectedDate,
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedDate.isEmpty()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Calendar Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        if (expanded.value) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selected = "$dayOfMonth/${month + 1}/$year"
                    onFechaChange(selected)
                    expanded.value = false
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.setOnDismissListener {
                expanded.value = false
            }
            datePickerDialog.show()
        }
    }
}
