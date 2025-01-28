package edu.ucne.composedemo.presentation.tickets

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.data.local.Dao.PrioridadDao
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.data.repository.TicketRepository
import kotlinx.coroutines.launch


@Composable
fun EditTicketScreen(
    navController: NavController,
    ticketId: Int,
    ticketDao: TicketDao,
    clienteDao: ClienteDao,
    tecnicoDao: TecnicoDao,
    prioridadDao: PrioridadDao
) {
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val ticketRepository = TicketRepository(db.ticketDao())

    // Estados para los campos del ticket
    val fecha = remember { mutableStateOf("") }
    val asunto = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }

    // Obtener las listas de clientes, técnicos y prioridades
    val clienteList by clienteDao.getAll().collectAsState(initial = emptyList())
    val tecnicoList by tecnicoDao.getAll().collectAsState(initial = emptyList())
    val prioridadList by prioridadDao.getAll().collectAsState(initial = emptyList())

    // Estados para los valores seleccionados
    val selectedCliente = remember { mutableStateOf<ClienteEntity?>(null) }
    val selectedTecnico = remember { mutableStateOf<TecnicoEntity?>(null) }
    val selectedPrioridad = remember { mutableStateOf<PrioridadEntity?>(null) }

    // Lanza una coroutine para cargar los datos del ticket cuando las listas estén listas
    LaunchedEffect(ticketId, clienteList, tecnicoList, prioridadList) {
        if (clienteList.isNotEmpty() && tecnicoList.isNotEmpty() && prioridadList.isNotEmpty()) {
            val ticket = ticketRepository.getTicket(ticketId)
            ticket?.let { currentTicket ->
                // Asignar los valores relacionados al ticket
                selectedCliente.value = clienteList.find { it.clienteId == currentTicket.clienteId }
                selectedTecnico.value = tecnicoList.find { it.tecnicoId == currentTicket.tecnicoId }
                selectedPrioridad.value = prioridadList.find { it.prioridadId == currentTicket.prioridadId }

                // Asignar valores de los campos del ticket
                fecha.value = currentTicket.fecha
                asunto.value = currentTicket.asunto
                descripcion.value = currentTicket.descripcion
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()

    // Variables para el DatePickerDialog
    val calendar = remember { java.util.Calendar.getInstance() }
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fecha.value = "$year-${month + 1}-$dayOfMonth"
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Mostrar un indicador de carga mientras las listas están vacías
        if (clienteList.isEmpty() || tecnicoList.isEmpty() || prioridadList.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Título de la pantalla
            Text(
                text = "Editar Ticket",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Seleccionar Cliente
            DropDownMenu(
                label = "Seleccionar Cliente",
                items = clienteList.map { it.nombre },
                selectedItem = selectedCliente.value?.nombre ?: "",
                onItemSelected = { selectedItem ->
                    selectedCliente.value = clienteList.find { it.nombre == selectedItem }
                }
            )

            // Seleccionar Técnico
            DropDownMenu(
                label = "Seleccionar Técnico",
                items = tecnicoList.map { it.nombres },
                selectedItem = selectedTecnico.value?.nombres ?: "",
                onItemSelected = { selectedItem ->
                    selectedTecnico.value = tecnicoList.find { it.nombres == selectedItem }
                }
            )

            // Seleccionar Prioridad
            DropDownMenu(
                label = "Seleccionar Prioridad",
                items = prioridadList.map { it.nivel },
                selectedItem = selectedPrioridad.value?.nivel ?: "",
                onItemSelected = { selectedItem ->
                    selectedPrioridad.value = prioridadList.find { it.nivel == selectedItem }
                }
            )

            // Campo de texto para la fecha con selector de calendario
            OutlinedTextField(
                value = fecha.value,
                onValueChange = {},
                label = { Text("Fecha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                readOnly = true // Solo permite seleccionar desde el DatePickerDialog
            )

            // Campo de texto para el asunto
            OutlinedTextField(
                value = asunto.value,
                onValueChange = { asunto.value = it },
                label = { Text("Asunto") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo de texto para la descripción
            OutlinedTextField(
                value = descripcion.value,
                onValueChange = { descripcion.value = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para guardar el ticket
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            // Crear y guardar el ticket actualizado
                            val updatedTicket = TicketEntity(
                                ticketId = ticketId,
                                clienteId = selectedCliente.value?.clienteId ?: 0,
                                tecnicoId = selectedTecnico.value?.tecnicoId ?: 0,
                                prioridadId = selectedPrioridad.value?.prioridadId ?: 0,
                                fecha = fecha.value,
                                asunto = asunto.value,
                                descripcion = descripcion.value
                            )

                            // Guardar el ticket
                            ticketRepository.saveTicket(updatedTicket)

                            // Mostrar mensaje de éxito
                            Toast.makeText(context, "Ticket actualizado", Toast.LENGTH_SHORT).show()

                            // Navegar de regreso
                            navController.popBackStack()
                        } catch (e: Exception) {
                            // Manejar errores para evitar el cierre de la app
                            Toast.makeText(context, "Error al guardar el ticket: ${e.message}", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Guardar Cambios")
            }
        }
    }
}









