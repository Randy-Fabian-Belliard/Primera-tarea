package edu.ucne.composedemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.presentation.prioridades.PrioridadListScreen
import edu.ucne.composedemo.presentation.prioridades.PrioridadScreen
import edu.ucne.composedemo.presentation.prioridades.EditPrioridadScreen

@Composable
fun PrioridadHost() {
    val navController = rememberNavController()

    // Crear la instancia de la base de datos y el Dao
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val prioridadDao = db.prioridadDao()

    // Obtener la lista de prioridades
    val prioridadList by prioridadDao.getAll().collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = "prioridad_list_screen") {
        composable("prioridad_list_screen") {
            // Pasar el prioridadDao directamente a la pantalla PrioridadListScreen
            PrioridadListScreen(navController = navController, prioridadList = prioridadList, prioridadDao = prioridadDao)
        }

        composable("prioridad_screen/{prioridadId}") { backStackEntry ->
            val prioridadId = backStackEntry.arguments?.getString("prioridadId")?.toIntOrNull() ?: -1
            PrioridadScreen(navController = navController, prioridadId = prioridadId)
        }

        composable("edit_prioridad_screen/{prioridadId}") { backStackEntry ->
            val prioridadId = backStackEntry.arguments?.getString("prioridadId")?.toIntOrNull()
            EditPrioridadScreen(navController = navController, prioridadId = prioridadId)
        }
    }
}
