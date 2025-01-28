package edu.ucne.composedemo.presentation.tickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.entities.TicketEntity
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.data.local.Dao.ComentarioDao
import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.ComentarioEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults


import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun TicketListScreen(
    navController: NavController,
    ticketList: List<TicketEntity>,
    ticketDao: TicketDao,
    tecnicoDao: TecnicoDao,
    prioridadDao: PrioridadDao,
    clienteDao: ClienteDao,
    tecnicoList: List<TecnicoEntity>,
    clientesList: List<ClienteEntity>,
    prioridadesList: List<PrioridadEntity>,
    comentarioDao: ComentarioDao
) {
    // Recordar el CoroutineScope
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la lista de tickets
        Text(
            text = "Lista de Tickets",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Mostrar la lista de tickets
        ticketList.forEach { ticket ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                // Buscar el nombre del técnico correspondiente al tecnicoId
                val tecnico = tecnicoList.find { it.tecnicoId == ticket.tecnicoId }
                val tecnicoNombre = tecnico?.nombres ?: "Técnico no disponible"

                // Información del ticket
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Técnico: $tecnicoNombre",  // Mostrar el nombre del técnico
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                    Text(
                        text = "Asunto: ${ticket.asunto}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Fecha: ${ticket.fecha}",
                        style = TextStyle(fontWeight = FontWeight.Normal)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                // Botón de editar
                Button(
                    onClick = {
                        // Navegar a la pantalla de edición, pasando el ID del ticket
                        navController.navigate("edit_ticket_screen/${ticket.ticketId}")
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
                        // Lanzar corutina para eliminar los comentarios y el ticket
                        coroutineScope.launch {
                            try {
                                // Eliminar los comentarios asociados al ticket
                                comentarioDao.deleteByTicketId(ticket.ticketId ?: 0)
                                // Luego, eliminar el ticket
                                ticketDao.deleteById(ticket.ticketId ?: 0)
                            } catch (e: Exception) {
                                e.printStackTrace() // Para ver el error en los logs
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Color de fondo del botón
                    )
                ) {
                    Text("Eliminar")
                }

                // Botón de comentar
                Button(
                    onClick = {
                        // Navegar a la pantalla de comentarios, pasando el ID del ticket
                        navController.navigate("comments_screen/${ticket.ticketId}")
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue // Color de fondo del botón
                    )
                ) {
                    Text("Comentar")
                }
            }
        }

        // Botón para agregar un nuevo ticket
        Button(
            onClick = {
                // Navegar a la pantalla de registro de un nuevo ticket, pasando -1 como ticketId
                navController.navigate("ticket_screen/-1")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Agregar Ticket")
        }
    }
}

