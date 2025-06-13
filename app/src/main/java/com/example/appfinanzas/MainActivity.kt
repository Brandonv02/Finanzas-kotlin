package com.example.appfinanzas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.appfinanzas.data.UserPreferences
import com.example.appfinanzas.screens.FinanceHomeScreen
import com.example.appfinanzas.screens.LoginScreen
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



