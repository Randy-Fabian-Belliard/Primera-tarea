package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import edu.ucne.composedemo.presentation.navigation.AppNavigationWithTopBar
import edu.ucne.composedemo.presentation.navigation.NavigationHost


import edu.ucne.composedemo.ui.theme.PrimeraTareaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeraTareaTheme  {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Directamente utilizar el ViewModel de NavigationHost
                   // NavigationHost()
                    AppNavigationWithTopBar()
                }
            }
        }
    }
}


