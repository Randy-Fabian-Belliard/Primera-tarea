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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PrioridadScreen(navController: NavController, prioridadId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val prioridadDao = db.prioridadDao()

    // Usamos remember para almacenar los estados
    val nivel = remember { mutableStateOf("") }

    if (prioridadId != null && prioridadId != -1) {
        // Cargar datos de la prioridad si no es nueva
        // Aquí puedes cargar los datos de la prioridad de la base de datos usando prioridadDao.find(prioridadId)
    }

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {

        // Título de la lista de prioridades centrado
        Text(
            text = "Registro de Prioridades",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Cambiar el tamaño del texto
            ),
            modifier = Modifier
                .padding(bottom = 16.dp) // Espaciado debajo del título
                .align(Alignment.CenterHorizontally) // Centrado del título
        )

        OutlinedTextField(
            value = nivel.value, // Accedemos al valor con ".value"
            onValueChange = { nivel.value = it }, // Cambiamos el valor de nivel
            label = { Text("Nivel de Prioridad") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nivel.value.isNotBlank()) {
                    scope.launch {
                        val prioridad = PrioridadEntity(
                            nivel = nivel.value
                        )
                        if (prioridadId == -1) {
                            // Si es una nueva prioridad, la guardamos
                            prioridadDao.save(prioridad)
                        } else {
                            // Aquí podrías actualizar la prioridad en la base de datos
                        }
                        navController.popBackStack()  // Volver a la lista
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Prioridad")
        }
    }
}
