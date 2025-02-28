package edu.ucne.erick_p2_p2.presentation.screen.deposito


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.erick_p2_p2.data.remote.dto.DepositoDto



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexDepositosScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    onCreateDeposito: () -> Unit,
    onEditDeposito: (Int) -> Unit,
    onDeleteDeposito: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Depositos") },
                navigationIcon = {
                    IconButton(onClick = {
                        onDrawerToggle()
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreateDeposito() },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            if (uiState.depositos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "No Depositos",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No se han encontrado ningún registro de Depositos",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(uiState.depositos) { Deposito ->
                        DepositoCard(
                            Deposito = Deposito,
                            onEditDeposito = onEditDeposito,
                            onDeleteDeposito = onDeleteDeposito
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DepositoCard(
    Deposito: DepositoDto,
    onEditDeposito: (Int) -> Unit,
    onDeleteDeposito: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEditDeposito(Deposito.idDeposito ?: 0) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Descripción: ${Deposito.concepto}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "MONTO: ${Deposito.monto}")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditDeposito(Deposito.idDeposito ?: 0) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { onDeleteDeposito(Deposito.idDeposito ?: 0) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
