package edu.ucne.composedemo.presentation.tecnicos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.*
import kotlinx.coroutines.launch
import edu.ucne.composedemo.data.local.database.TecnicoDb
import androidx.compose.ui.platform.LocalContext
import edu.ucne.composedemo.data.local.Dao.TecnicoDao
import edu.ucne.composedemo.data.local.entities.TecnicoEntity


@Composable
fun TecnicoScreen() {
    // Crea la instancia de la base de datos
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            TecnicoDb::class.java,
            "tecnico_db"
        ).build()
    }
    val tecnicoDao = db.tecnicoDao() //

    var nombres by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Nombres") },
                        value = nombres,
                        onValueChange = { nombres = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Sueldo") },
                        value = sueldo,
                        onValueChange = { sueldo = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {
                                nombres = ""
                                sueldo = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text(text = "Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        OutlinedButton(
                            onClick = {
                                if (nombres.isBlank() || sueldo.isBlank()) {
                                    errorMessage = "Todos los campos son obligatorios"
                                    return@OutlinedButton
                                }
                                scope.launch {
                                    tecnicoDao.save(
                                        TecnicoEntity(
                                            nombres = nombres,
                                            sueldo = sueldo.toFloatOrNull() ?: 0f
                                        )
                                    )
                                    nombres = ""
                                    sueldo = ""
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }
                    }
                }
            }

            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            val tecnicoList by tecnicoDao.getAll()
                .collectAsStateWithLifecycle(
                    initialValue = emptyList(),
                    lifecycleOwner = lifecycleOwner,
                    minActiveState = Lifecycle.State.STARTED
                )
            TecnicoListScreen(tecnicoList)
        }
    }
}


/*
@Composable
    fun TecnicoScreen() {
        var nombres by remember { mutableStateOf("") }
        var sueldo by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            label = { Text(text = "Nombres") },
                            value = nombres,
                            onValueChange = { nombres = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Sueldo") },
                            value = sueldo,
                            onValueChange = { sueldo = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {
                                    nombres = ""
                                    sueldo = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "new button"
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (nombres.isBlank() || sueldo.isBlank()) {
                                        errorMessage = "Todos los campos son obligatorios"
                                        return@OutlinedButton
                                    }
                                    scope.launch {
                                        saveTecnico(
                                            TecnicoEntity(
                                                nombres = nombres,
                                                sueldo = sueldo.toFloatOrNull()
                                            )
                                        )
                                        nombres = ""
                                        sueldo = ""
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }

                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val tecnicoList by TecnicoDb.tecnicoDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                TecnicoListScreen(tecnicoList)
            }
        }
    }*/

/*
private suspend fun saveTecnico(tecnico: TecnicoEntity) {
    TecnicoDb.tecnicoDao().save(tecnico)

}*/


private suspend fun saveTecnico(tecnicoDao: TecnicoDao, tecnico: TecnicoEntity) {
    tecnicoDao.save(tecnico)
}
