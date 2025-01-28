package edu.ucne.composedemo.presentation.navigation

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*
import edu.ucne.composedemo.presentation.clientes.ClienteNavigationHost
import edu.ucne.composedemo.presentation.prioridades.PrioridadNavigationHost
import edu.ucne.composedemo.presentation.tickets.TicketNavigationHost

data class Tecnico(val id: Int, val nombre: String, val especialidad: String)

@Composable
fun AppNavigationWithTopBar() {
    var isDrawerOpen by remember { mutableStateOf(false) }
    val navController = rememberNavController()  // Se necesita el NavController para la navegación

    // Contenido del Drawer
    val drawerContent = @Composable {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Técnicos",
                modifier = Modifier
                    .clickable {
                        // Navegar a la pantalla de técnicos
                        navController.navigate("tecnicos")
                        isDrawerOpen = false // Cerrar el drawer cuando navegue
                    }
                    .padding(16.dp)
            )
            Text(
                text = "Clientes",
                modifier = Modifier
                    .clickable {
                        navController.navigate("clientes")
                        isDrawerOpen = false
                    }
                    .padding(16.dp)
            )
            Text(
                text = "Prioridades",
                modifier = Modifier
                    .clickable {
                        navController.navigate("prioridades")
                        isDrawerOpen = false
                    }
                    .padding(16.dp)
            )

            Text(
                text = "Tickets",
                modifier = Modifier
                    .clickable {
                        navController.navigate("tickets")
                        isDrawerOpen = false
                    }
                    .padding(16.dp)
            )


        }
    }

    // Contenido principal con el Drawer
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Barra superior con botón de apertura del Drawer
            TopAppBar(
                title = { Text("Gestión de Técnicos y Clientes") },
                backgroundColor = Color.Gray,
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = { isDrawerOpen = !isDrawerOpen }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Abrir menú")
                    }
                }
            )
            // Aquí estará el contenido principal (donde se muestra la lista de técnicos o cualquier otra pantalla)
            NavHost(
                navController = navController,
                startDestination = "home", // Página inicial
                modifier = Modifier.padding(16.dp)
            ) {
                composable("home") {
                    // Aquí irá el contenido principal si no se ha navegado
                    //NavigationHost()

                }
                composable("tecnicos") {
                    NavigationHost()
                }

                composable("clientes") {
                    ClienteNavigationHost()
                }

                composable("prioridades") {
                    PrioridadNavigationHost()
                }
                composable("tickets") {
                    TicketNavigationHost()
                }
            }
        }

        // Mostrar el Drawer (cuadro flotante)
        if (isDrawerOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)) // Fondo oscuro semi-transparente
                    .clickable { isDrawerOpen = false } // Cerrar Drawer al tocar fuera
            )
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
                    .background(Color.White)
                    .align(Alignment.CenterStart)
            ) {
                drawerContent() // Aquí se invoca la función de contenido del Drawer
            }
        }
    }
}





@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    actions: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.padding(start = 16.dp)) {
            title()
        }
        actions()
    }
}

@Preview
@Composable
fun PreviewAppNavigationWithTopBar() {
    AppNavigationWithTopBar()
}
