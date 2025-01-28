package edu.ucne.composedemo.presentation.navigation

import edu.ucne.composedemo.data.local.entities.ClienteEntity
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.data.local.entities.TecnicoEntity
import kotlinx.serialization.Serializable



sealed class Screen {
    @Serializable
    data object TecnicoScreen : Screen()

    @Serializable
    data class TecnicoListScreen(val tecnicoList: List<TecnicoEntity>) : Screen()

    @Serializable
    data object ClienteScreen : Screen()

    @Serializable
    data class ClienteListScreen(val clienteList: List<ClienteEntity>) : Screen()

    @Serializable
    data object PrioridadScreen : Screen()

    @Serializable
    data class PrioridadListScreen(val prioridadList: List<PrioridadEntity>) : Screen()


}






