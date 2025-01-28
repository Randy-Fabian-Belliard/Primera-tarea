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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun TecnicoScreen(navController: NavController, tecnicoId: Int?) {
    val context = LocalContext.current
    val db = TecnicoDb.getDatabase(context)
    val tecnicoDao = db.tecnicoDao()

    // Usamos remember para almacenar los estados
    val nombres = remember { mutableStateOf("") }
    val sueldo = remember { mutableStateOf("") }

    if (tecnicoId != null && tecnicoId != -1) {
        // Cargar datos del técnico si no es un nuevo técnico
        // Aquí puedes cargar los datos del técnico de la base de datos usando tecnicoDao.find(tecnicoId)
    }

    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.padding(16.dp)) {

        // Título de la lista de técnicos centrado
        Text(
            text = "Registro de tecnicos",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Cambiar el tamaño del texto
            ),
            modifier = Modifier
                .padding(bottom = 16.dp) // Espaciado debajo del título
                .align(Alignment.CenterHorizontally) // Centrado del título
        )

        OutlinedTextField(
            value = nombres.value, // Accedemos al valor con ".value"
            onValueChange = { nombres.value = it }, // Cambiamos el valor de nombres
            label = { Text("Nombre del Técnico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sueldo.value, // Accedemos al valor con ".value"
            onValueChange = { sueldo.value = it }, // Cambiamos el valor de sueldo
            label = { Text("Sueldo del Técnico") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombres.value.isNotBlank() && sueldo.value.isNotBlank()) {
                    scope.launch {
                        val tecnico = TecnicoEntity(
                            nombres = nombres.value,
                            sueldo = sueldo.value.toFloatOrNull() ?: 0f
                        )
                        if (tecnicoId == -1) {
                            // Si es un nuevo técnico, lo guardamos
                            tecnicoDao.save(tecnico)
                        } else {
                            // Aquí podrías actualizar el técnico en la base de datos
                        }
                        navController.popBackStack()  // Volver a la lista
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Técnico")
        }
    }
}









