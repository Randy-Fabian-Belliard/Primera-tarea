package edu.ucne.composedemo.presentation.clientes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.Dao.ClienteDao
import edu.ucne.composedemo.presentation.clientes.ClienteListScreen
import edu.ucne.composedemo.presentation.clientes.ClienteScreen
import edu.ucne.composedemo.presentation.clientes.EditClienteScreen


@Composable
fun ClienteNavigationHost() {
    val navController = rememberNavController()

    // Crear la instancia de la base de datos y el Dao
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val clienteDao = db.clienteDao()

    // Obtener la lista de clientes
    val clienteList by clienteDao.getAll().collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = "cliente_list_screen") {
        composable("cliente_list_screen") {
            // Pasar el clienteDao directamente a la pantalla ClienteListScreen
            ClienteListScreen(navController = navController, clienteList = clienteList, clienteDao = clienteDao)
        }

        composable("cliente_screen/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toIntOrNull() ?: -1
            ClienteScreen(navController = navController, clienteId = clienteId)
        }
        composable("edit_cliente_screen/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toIntOrNull()
            EditClienteScreen(navController = navController, clienteId = clienteId)
        }
    }
}
