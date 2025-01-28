package edu.ucne.composedemo.presentation.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.entities.ClienteEntity

@Composable
fun EditClienteScreen(navController: NavController, clienteId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val clienteDao = db.clienteDao()

    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Cargar datos del cliente si el ID es válido
    if (clienteId != null && clienteId != -1) {
        scope.launch {
            val cliente = clienteDao.find(clienteId)
            if (cliente != null) {
                nombre.value = cliente.nombre
                correo.value = cliente.correo
                telefono.value = cliente.telefono
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la pantalla
        Text(
            text = "Editar Cliente",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Campo de texto para el nombre
        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el correo
        OutlinedTextField(
            value = correo.value,
            onValueChange = { correo.value = it },
            label = { Text("Correo del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el teléfono
        OutlinedTextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text("Teléfono del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar los cambios
        Button(
            onClick = {
                if (nombre.value.isNotBlank() && correo.value.isNotBlank() && telefono.value.isNotBlank()) {
                    scope.launch {
                        val updatedCliente = ClienteEntity(
                            clienteId = clienteId,
                            nombre = nombre.value,
                            correo = correo.value,
                            telefono = telefono.value
                        )
                        clienteDao.save(updatedCliente)
                        navController.popBackStack() // Volver a la pantalla anterior
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Cambios")
        }
    }
}
