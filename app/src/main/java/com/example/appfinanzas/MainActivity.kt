package com.example.appfinanzas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appfinanzas.data.UserPreferences
import com.example.appfinanzas.screens.FinanceHomeScreen
import com.example.appfinanzas.screens.LoginScreen
import com.example.appfinanzas.ui.theme.AppFinanzasTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val context = this
            val scope = rememberCoroutineScope()

            var isLoggedIn by remember { mutableStateOf(false) }
            var userName by remember { mutableStateOf("") }
            var userSalary by remember { mutableStateOf(0.0) }

            // Cargar usuario guardado
            LaunchedEffect(Unit) {
                val (savedName, savedSalary) = UserPreferences.loadUser(context)
                if (savedName != null && savedSalary != null) {
                    userName = savedName
                    userSalary = savedSalary
                    isLoggedIn = true
                }
            }

            MaterialTheme {
                if (!isLoggedIn) {
                    LoginScreen { name, salary ->
                        userName = name
                        userSalary = salary
                        isLoggedIn = true

                        // Guardar datos usando scope
                        scope.launch {
                            UserPreferences.saveUser(context, name, salary)
                        }
                    }
                } else {
                    FinanceHomeScreen()
                }
            }
        }
    }
}



