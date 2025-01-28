package edu.ucne.composedemo.presentation.tickets

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.repository.TicketRepository
import edu.ucne.composedemo.data.local.entities.TicketEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TicketScreen(navController: NavController, ticketId: Int?) {
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val clienteDao = db.clienteDao()
    val tecnicoDao = db.tecnicoDao()
    val prioridadDao = db.prioridadDao()
    val ticketRepository = TicketRepository(db.ticketDao())

    // Obtener las listas de clientes, técnicos y prioridades
    val clienteList by clienteDao.getAll().collectAsState(initial = emptyList())
    val tecnicoList by tecnicoDao.getAll().collectAsState(initial = emptyList())
    val prioridadList by prioridadDao.getAll().collectAsState(initial = emptyList())

    // Fecha por defecto: día de hoy
    val todayDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val fecha = remember { mutableStateOf(todayDate) }
    val asunto = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }

    // Valores seleccionados para las llaves foráneas
    val selectedCliente = remember { mutableStateOf<ClienteEntity?>(null) }
    val selectedTecnico = remember { mutableStateOf<TecnicoEntity?>(null) }
    val selectedPrioridad = remember { mutableStateOf<PrioridadEntity?>(null) }

    // Lanza una coroutine para poder llamar a la función suspendida
    val coroutineScope = rememberCoroutineScope()

    // Calendario para seleccionar fecha
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            fecha.value = selectedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // Título de la pantalla
        Text(
            text = "Crear Ticket",
            modifier = Modifier.padding(bottom = 16.dp)
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

        // Campo de texto para la fecha con un calendario
        OutlinedTextField(
            value = fecha.value,
            onValueChange = {},
            label = { Text("Fecha (dd/MM/yyyy)") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }, // Muestra el DatePickerDialog al hacer clic
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Abrir calendario",
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            }
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
                if (isValidDate(fecha.value)) {
                    val formattedDate = formatDate(fecha.value) // Formatea la fecha al formato deseado
                    val newTicket = TicketEntity(
                        clienteId = selectedCliente.value?.clienteId ?: 0,
                        tecnicoId = selectedTecnico.value?.tecnicoId ?: 0,
                        prioridadId = selectedPrioridad.value?.prioridadId ?: 0,
                        fecha = formattedDate, // Guardar fecha formateada
                        asunto = asunto.value,
                        descripcion = descripcion.value
                    )

                    coroutineScope.launch {
                        try {
                            ticketRepository.saveTicket(newTicket)
                            navController.popBackStack() // Regresar a la pantalla anterior
                            Toast.makeText(context, "Ticket guardado correctamente", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Fecha inválida. Use el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Guardar Ticket")
        }
    }
}

@Composable
fun DropDownMenu(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            label = { Text(label) },
            value = selectedItem,
            onValueChange = {},  // No cambia el valor porque es readonly
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable { expanded = true },  // Abre el menú al hacer clic
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                )
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(
                with(LocalDensity.current) {
                    textFieldSize.width.toDp()
                }
            )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)  // Actualiza el elemento seleccionado
                        expanded = false  // Cierra el menú
                    },
                    text = { Text(text = item) }
                )
            }
        }
    }
}

// Función para validar si el formato de fecha es correcto
fun isValidDate(date: String): Boolean {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false // No permite fechas no válidas, como 30/02/2023
    return try {
        dateFormat.parse(date)
        true
    } catch (e: Exception) {
        false
    }
}

// Función para formatear la fecha al formato deseado
fun formatDate(date: String): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val parsedDate = dateFormat.parse(date)
    return dateFormat.format(parsedDate!!)
}





