package edu.ucne.composedemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.presentation.comentario.CommentsScreen
import edu.ucne.composedemo.presentation.tecnicos.EditTecnicoScreen
import edu.ucne.composedemo.presentation.tecnicos.TecnicoListScreen
import edu.ucne.composedemo.presentation.tecnicos.TecnicoScreen


@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    // Crear la instancia de la base de datos y el Dao
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val tecnicoDao = db.tecnicoDao()
    val clienteDao = db.clienteDao()
    val comentarioDao = db.ComentarioDao()
    val ticketDao = db.ticketDao()

    // Obtener la lista de técnicos
    val tecnicoList by tecnicoDao.getAll().collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = "tecnico_list_screen") {
        composable("tecnico_list_screen") {
            // Pasar el tecnicoDao directamente a la pantalla TecnicoListScreen
            TecnicoListScreen(navController = navController, tecnicoList = tecnicoList, tecnicoDao = tecnicoDao)
        }

        composable("tecnico_screen/{tecnicoId}") { backStackEntry ->
            val tecnicoId = backStackEntry.arguments?.getString("tecnicoId")?.toIntOrNull() ?: -1
            TecnicoScreen(navController = navController, tecnicoId = tecnicoId)
        }

        composable("edit_tecnico_screen/{tecnicoId}") { backStackEntry ->
            val tecnicoId = backStackEntry.arguments?.getString("tecnicoId")?.toIntOrNull()
            EditTecnicoScreen(navController = navController, tecnicoId = tecnicoId)
        }



    }
}









