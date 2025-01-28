package edu.ucne.composedemo.presentation.clientes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ClienteListScreen(navController: NavController, clienteList: List<ClienteEntity>, clienteDao: ClienteDao) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la lista de clientes
        Text(
            text = "Lista de Clientes",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Mostrar la lista de clientes
        clienteList.forEach { cliente ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                // Información del cliente
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Nombre: ${cliente.nombre}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Correo: ${cliente.correo}",
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                    Text(
                        text = "Teléfono: ${cliente.telefono}",
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                }

                // Botón de editar
                Button(
                    onClick = {
                        // Navegar a la pantalla de edición, pasando el ID del cliente
                        navController.navigate("edit_cliente_screen/${cliente.clienteId}")
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
                        // Eliminar el cliente directamente desde el Dao
                        CoroutineScope(Dispatchers.IO).launch {
                            clienteDao.delete(cliente)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }

        // Botón para agregar un nuevo cliente
        Button(
            onClick = {
                // Navegar a la pantalla de registro de un nuevo cliente, pasando -1 como clienteId
                navController.navigate("cliente_screen/-1")
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Agregar Cliente")
        }
    }
}
