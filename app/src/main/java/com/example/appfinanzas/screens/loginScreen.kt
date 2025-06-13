package com.example.appfinanzas.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onContinue: (String, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    // Fondo degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1976D2),
                        Color(0xFF386DA8),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) {
        // Tarjeta central elevada
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.97f))
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .widthIn(min = 320.dp, max = 400.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar superior
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1976D2).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Avatar",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¡Bienvenido!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2),
                        fontSize = 28.sp
                    )
                )
                Text(
                    text = "Gestiona tus finanzas fácilmente",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF1976D2).copy(alpha = 0.7f),
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Campo nombre con ícono
                OutlinedTextField(
                    value = name,
                    onValueChange = { if (!isSaved) name = it },
                    label = { Text("Tu nombre") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Nombre"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    enabled = !isSaved,
                    shape = RoundedCornerShape(16.dp)
                )

                // Campo sueldo con ícono
                OutlinedTextField(
                    value = salary,
                    onValueChange = { if (!isSaved) salary = it },
                    label = { Text("Sueldo mensual") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Sueldo"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    enabled = !isSaved,
                    shape = RoundedCornerShape(16.dp)
                )

                // Mensaje de error animado
                AnimatedVisibility(
                    visible = showError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        "Por favor completa ambos campos correctamente.",
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón con animación de guardado
                Button(
                    onClick = {
                        val sueldoDouble = salary.toDoubleOrNull()
                        if (name.isNotBlank() && sueldoDouble != null) {
                            showError = false
                            isSaved = true
                            onContinue(name, sueldoDouble)
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSaved) Color(0xFF388E3C) else Color(0xFF1976D2)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isSaved
                ) {
                    Text(
                        if (isSaved) "¡Guardado!" else "Ingresar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
