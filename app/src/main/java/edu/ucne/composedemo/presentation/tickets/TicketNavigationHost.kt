package edu.ucne.composedemo.presentation.tickets

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
import edu.ucne.composedemo.data.local.Dao.ComentarioDao
import edu.ucne.composedemo.data.local.database.TecnicoDb
import edu.ucne.composedemo.data.local.Dao.TicketDao
import edu.ucne.composedemo.presentation.comentario.CommentsScreen
import edu.ucne.composedemo.presentation.tickets.TicketListScreen


@Composable
fun TicketNavigationHost() {
    val navController = rememberNavController()

    // Crear la instancia de la base de datos y el Dao
    val context = LocalContext.current
    val db = remember { TecnicoDb.getDatabase(context) }
    val ticketDao = db.ticketDao()
    val tecnicoDao = db.tecnicoDao()
    val prioridadDao = db.prioridadDao()
    val clienteDao = db.clienteDao()

    val comentarioDao = db.ComentarioDao()


    // Obtener la lista de tickets
    val ticketList by ticketDao.getAll().collectAsState(initial = emptyList())
    val clientesList by clienteDao.getAll().collectAsState(initial = emptyList())
    val prioridadesList by prioridadDao.getAll().collectAsState(initial = emptyList())
    val tecnicoList by tecnicoDao.getAll().collectAsState(initial = emptyList())


   NavHost(navController = navController, startDestination = "ticket_list_screen") {

        composable("ticket_list_screen") {
            // Pasar el ticketDao directamente a la pantalla TicketListScreen
            TicketListScreen(
                navController = navController,
                ticketList = ticketList,
                ticketDao = ticketDao,
                clientesList = clientesList,
                clienteDao = clienteDao,
                prioridadesList = prioridadesList,
                prioridadDao = prioridadDao,
                tecnicoList = tecnicoList,
                tecnicoDao = tecnicoDao,
                comentarioDao = comentarioDao
            )
       }

        composable("ticket_screen/{ticketId}") { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId")?.toIntOrNull() ?: -1
            TicketScreen(navController = navController, ticketId = ticketId)
        }

        composable("edit_ticket_screen/{ticketId}") { backStackEntry ->
            // Proporciona un valor predeterminado si ticketId es nulo
            val ticketId = backStackEntry.arguments?.getString("ticketId")?.toIntOrNull() ?: -1

            // Asegúrate de pasar los DAO necesarios
            EditTicketScreen(
                navController = navController,
                ticketId = ticketId,  // Aquí pasas el ticketId con valor predeterminado
                ticketDao = ticketDao,
                clienteDao = clienteDao,
                tecnicoDao = tecnicoDao,
                prioridadDao = prioridadDao
            )
        }

       composable("comments_screen/{ticketId}") { backStackEntry ->
           val ticketId = backStackEntry.arguments?.getString("ticketId")?.toIntOrNull() ?: -1
           val tecnicoId = backStackEntry.arguments?.getString("tecnicoId")?.toIntOrNull() ?: -1
           CommentsScreen(
               navController = navController,
               ticketId = ticketId,

               comentarioDao = comentarioDao,
               ticketDao = ticketDao,
               tecnicoDao = tecnicoDao
           )
       }

    }
}
