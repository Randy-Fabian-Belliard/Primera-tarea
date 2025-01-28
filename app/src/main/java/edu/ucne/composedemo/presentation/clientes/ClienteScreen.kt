package edu.ucne.composedemo.presentation.clientes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import kotlinx.coroutines.launch

@Composable
fun ClienteScreen(navController: NavController, clienteId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val clienteDao = db.clienteDao()

    // Estados para los campos del formulario
    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Cargar datos del cliente si clienteId no es -1
    LaunchedEffect(clienteId) {
        if (clienteId != null && clienteId != -1) {
            val cliente = clienteDao.find(clienteId)
            cliente?.let {
                nombre.value = it.nombre
                correo.value = it.correo
                telefono.value = it.telefono
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        // Título centrado
        Text(
            text = "Registro de Clientes",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Campos del formulario
        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo.value,
            onValueChange = { correo.value = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text("Teléfono del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar
        Button(
            onClick = {
                if (nombre.value.isNotBlank() && correo.value.isNotBlank() && telefono.value.isNotBlank()) {
                    scope.launch {
                        val cliente = ClienteEntity(
                            clienteId = if (clienteId == -1) null else clienteId,
                            nombre = nombre.value,
                            correo = correo.value,
                            telefono = telefono.value
                        )
                        clienteDao.save(cliente) // Guardar o actualizar cliente
                        navController.popBackStack() // Volver a la lista
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Cliente")
        }
    }
}
