package edu.ucne.composedemo.presentation.tecnicos

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
import edu.ucne.composedemo.data.local.entities.TecnicoEntity

@Composable
fun EditTecnicoScreen(navController: NavController, tecnicoId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val tecnicoDao = db.tecnicoDao()

    val nombres = remember { mutableStateOf("") }
    val sueldo = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Cargar datos del técnico si el ID es válido
    if (tecnicoId != null && tecnicoId != -1) {
        scope.launch {
            val tecnico = tecnicoDao.find(tecnicoId)
            if (tecnico != null) {
                nombres.value = tecnico.nombres
                sueldo.value = tecnico.sueldo?.toString() ?: ""
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la pantalla
        Text(
            text = "Editar Técnico",
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
            value = nombres.value,
            onValueChange = { nombres.value = it },
            label = { Text("Nombre del Técnico") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para el sueldo
        OutlinedTextField(
            value = sueldo.value,
            onValueChange = { sueldo.value = it },
            label = { Text("Sueldo del Técnico") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar los cambios
        Button(
            onClick = {
                if (nombres.value.isNotBlank() && sueldo.value.isNotBlank()) {
                    scope.launch {
                        val updatedTecnico = TecnicoEntity(
                            tecnicoId = tecnicoId,
                            nombres = nombres.value,
                            sueldo = sueldo.value.toFloatOrNull() ?: 0f
                        )
                        tecnicoDao.save(updatedTecnico)
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


