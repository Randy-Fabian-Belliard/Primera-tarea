package edu.ucne.composedemo.presentation.tecnicos

import androidx.compose.foundation.clickable
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
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun TecnicoListScreen(navController: NavController, tecnicoList: List<TecnicoEntity>, tecnicoDao: TecnicoDao) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la lista de técnicos
        Text(
            text = "Lista de Técnicos",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Cambiar el tamaño del texto
            ),
            modifier = Modifier
                .padding(bottom = 16.dp) // Espaciado debajo del título
                .align(Alignment.CenterHorizontally) // Centrado del título
        )

        // Mostrar la lista de técnicos
        tecnicoList.forEach { tecnico ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                // Información del técnico
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Nombre: ${tecnico.nombres}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Sueldo: ${tecnico.sueldo}",
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                }

                // Botón de editar
                Button(
                    onClick = {
                        // Navegar a la pantalla de edición, pasando el ID del técnico
                        navController.navigate("edit_tecnico_screen/${tecnico.tecnicoId}")
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
                        // Eliminar el técnico directamente desde el Dao
                        CoroutineScope(Dispatchers.IO).launch {
                            tecnicoDao.deleteById(tecnico.tecnicoId ?: 0)
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

        // Botón para agregar un nuevo técnico
        Button(
            onClick = {
                // Navegar a la pantalla de registro de un nuevo técnico, pasando -1 como tecnicoId
                navController.navigate("tecnico_screen/-1")
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Agregar Técnico")
        }
    }
}










