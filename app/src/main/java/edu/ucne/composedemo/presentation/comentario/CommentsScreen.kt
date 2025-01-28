package edu.ucne.composedemo.presentation.comentario



import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.Dao.ComentarioDao
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.entities.ComentarioEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.presentation.tickets.DropDownMenu

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



@Composable
fun CommentsScreen(
    navController: NavController,
    ticketId: Int,
    comentarioDao: ComentarioDao,
    tecnicoDao: TecnicoDao,
    ticketDao: TicketDao
) {
    // Obtener el ticket seleccionado
    val ticketFlow = remember { ticketDao.findById(ticketId) }
    val ticket by ticketFlow.collectAsStateWithLifecycle(initialValue = null)

    // Obtener el técnico asignado al ticket
    var tecnicoAsignado by remember { mutableStateOf<TecnicoEntity?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(ticket) {
        if (ticket != null) {
            coroutineScope.launch {
                tecnicoAsignado = ticket?.tecnicoId?.let { tecnicoDao.findById(it) }
            }
        }
    }

    // Obtener los comentarios asociados al ticket
    val comentariosFlow = remember { comentarioDao.getByTicketId(ticketId) }
    val comentarios by comentariosFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    // Estado para el nuevo comentario
    var nuevoComentario by remember { mutableStateOf("") }

    // Estado para el técnico seleccionado
    var tecnicoSeleccionado by remember { mutableStateOf<TecnicoEntity?>(null) }

    // Estado para controlar la visibilidad del DropdownMenu
    var expanded by remember { mutableStateOf(false) }

    // Fondo gris para toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Fondo gris claro
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Encabezado con información del ticket
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Por: ${tecnicoAsignado?.nombres ?: "No asignado"}",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "en ${ticket?.fecha ?: "Fecha desconocida"}",
                    style = TextStyle(fontWeight = FontWeight.Normal)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Operador",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .background(Color.Blue, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Contenedor del ticket
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(3.dp))
                    .shadow(2.dp, shape = RoundedCornerShape(3.dp))
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ticket?.let {
                    Text(text = "Asunto: ${it.asunto}", style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = "Descripción: ${it.descripcion}", style = TextStyle(fontWeight = FontWeight.Normal))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de comentarios dentro de un LazyColumn para que sea desplazable
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(comentarios) { comentario ->
                    var tecnico by remember { mutableStateOf<TecnicoEntity?>(null) }

                    LaunchedEffect(comentario.tecnicoId) {
                        coroutineScope.launch {
                            tecnico = tecnicoDao.findById(comentario.tecnicoId)
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Por: ${tecnico?.nombres ?: "Técnico desconocido"}",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "en ${comentario.fecha}",
                            style = TextStyle(fontWeight = FontWeight.Normal)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Dueño",
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .background(Color(0xFF4CAF50), shape = RoundedCornerShape(4.dp)) // Verde más apagado
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Contenedor del comentario
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(2.dp))
                            .shadow(1.dp, shape = RoundedCornerShape(2.dp))
                            .padding(8.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = comentario.mensaje, style = TextStyle(fontWeight = FontWeight.Normal))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Componente para agregar un nuevo comentario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // Permite desplazamiento en el formulario
            ) {
                // Campo de texto para el nuevo comentario
                OutlinedTextField(
                    value = nuevoComentario,
                    onValueChange = { nuevoComentario = it },
                    label = { Text("Escribe un comentario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // Contenedor para el selector de técnico
                OutlinedTextField(
                    value = tecnicoSeleccionado?.nombres ?: "",
                    onValueChange = {},
                    label = { Text("Seleccionar Técnico") },
                    readOnly = true, // Hacemos el campo solo de lectura
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Seleccionar Técnico"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                // DropdownMenu para seleccionar al técnico
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start) // Alineamos el dropdown debajo del campo de texto
                ) {
                    tecnicoDao.getAll().collectAsStateWithLifecycle(initialValue = emptyList()).value.forEach { tecnico ->
                        DropdownMenuItem(
                            onClick = {
                                tecnicoSeleccionado = tecnico
                                expanded = false
                            },
                            text = { Text(text = tecnico.nombres) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón para agregar el nuevo comentario
                Button(
                    onClick = {
                        if (nuevoComentario.isNotBlank() && tecnicoSeleccionado != null) {
                            coroutineScope.launch {
                                val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                                comentarioDao.insert(
                                    ComentarioEntity(
                                        ticketId = ticketId,
                                        tecnicoId = tecnicoSeleccionado?.tecnicoId ?: 0,
                                        mensaje = nuevoComentario,
                                        fecha = currentDate
                                    )
                                )
                                nuevoComentario = ""
                                tecnicoSeleccionado = null
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Agregar Comentario")
                }
            }
        }
    }
}












