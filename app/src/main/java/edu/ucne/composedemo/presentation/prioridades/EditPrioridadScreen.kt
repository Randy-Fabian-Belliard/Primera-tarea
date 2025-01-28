package edu.ucne.composedemo.presentation.prioridades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.entities.PrioridadEntity

@Composable
fun EditPrioridadScreen(navController: NavController, prioridadId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val prioridadDao = db.prioridadDao()

    val nivel = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Cargar datos de la prioridad si el ID es válido
    if (prioridadId != null && prioridadId != -1) {
        scope.launch {
            val prioridad = prioridadDao.find(prioridadId)
            if (prioridad != null) {
                nivel.value = prioridad.nivel
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la pantalla
        Text(
            text = "Editar Prioridad",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Campo de texto para el nivel de prioridad
        OutlinedTextField(
            value = nivel.value,
            onValueChange = { nivel.value = it },
            label = { Text("Nivel de Prioridad") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar los cambios
        Button(
            onClick = {
                if (nivel.value.isNotBlank()) {
                    scope.launch {
                        val updatedPrioridad = PrioridadEntity(
                            prioridadId = prioridadId,
                            nivel = nivel.value
                        )
                        prioridadDao.save(updatedPrioridad)
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
