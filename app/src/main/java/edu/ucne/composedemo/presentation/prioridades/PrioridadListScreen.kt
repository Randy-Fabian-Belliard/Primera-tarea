package edu.ucne.composedemo.presentation.prioridades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PrioridadListScreen(navController: NavController, prioridadList: List<PrioridadEntity>, prioridadDao: PrioridadDao) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la lista de prioridades
        Text(
            text = "Lista de Prioridades",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Cambiar el tamaño del texto
            ),
            modifier = Modifier
                .padding(bottom = 16.dp) // Espaciado debajo del título
                .align(Alignment.CenterHorizontally) // Centrado del título
        )

        // Mostrar la lista de prioridades
        prioridadList.forEach { prioridad ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                // Información de la prioridad
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Nivel: ${prioridad.nivel}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }

                // Botón de editar
                Button(
                    onClick = {
                        // Navegar a la pantalla de edición, pasando el ID de la prioridad
                        navController.navigate("edit_prioridad_screen/${prioridad.prioridadId}")
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray
                    )
                ) {
                    Text("Editar")
                }

                // Botón de eliminar
                Button(
                    onClick = {
                        // Eliminar la prioridad directamente desde el Dao
                        CoroutineScope(Dispatchers.IO).launch {
                            prioridadDao.deleteById(prioridad.prioridadId ?: 0)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Color de fondo del botón
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }

        // Botón para agregar una nueva prioridad
        Button(
            onClick = {
                // Navegar a la pantalla de registro de una nueva prioridad, pasando -1 como prioridadId
                navController.navigate("prioridad_screen/-1")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Agregar Prioridad")
        }
    }
}
