package com.example.appfinanzas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.appfinanzas.components.DialogoAgregarGasto
import com.example.appfinanzas.components.HeaderSection

data class Expense(val amount: String, val category: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceHomeScreen() {
    var showAddExpenseDialog by remember { mutableStateOf(false) }
    val expenses = remember { mutableStateListOf<Expense>() }
    var fabMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { fabMenuExpanded = !fabMenuExpanded },
                    containerColor = Color(0xFF1976D2)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Menú")
                }
                DropdownMenu(
                    expanded = fabMenuExpanded,
                    onDismissRequest = { fabMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Inicio") },
                        onClick = { fabMenuExpanded = false /* TODO: Navegar */ },
                        leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Agregar") },
                        onClick = {
                            fabMenuExpanded = false
                            showAddExpenseDialog = true
                        },
                        leadingIcon = { Icon(Icons.Default.AddCircle, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Perfil") },
                        onClick = { fabMenuExpanded = false /* TODO: Ver perfil */ },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(innerPadding)
        ) {

            // HEADER
            HeaderSection()

            // BODY
            BodySection(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                expenses = expenses
            )
        }
    }

    // Diálogo para agregar gasto
    if (showAddExpenseDialog) {
        DialogoAgregarGasto(
            onDismiss = { showAddExpenseDialog = false },
            onAdd = { amount, category ->
                expenses.add(Expense(amount, category))
                showAddExpenseDialog = false
            }
        )
    }
}

@Composable
fun BodySection(modifier: Modifier = Modifier, expenses: List<Expense>) {
    Column(modifier = modifier) {
        Text("Resumen mensual", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Cálculo de totales
        val totalGastos = expenses.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
        val ingresos = 4000.0 // Fijo para ejemplo
        val balance = ingresos - totalGastos

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ingresos: $4,000")
                Text("Gastos: $${"%.2f".format(totalGastos)}")
                Text("Balance: $${"%.2f".format(balance)}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Últimas transacciones", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        if (expenses.isEmpty()) {
            Text("No hay gastos registrados.")
        } else {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                expenses.reversed().forEach { expense ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = expense.category,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "$${expense.amount}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

        }
    }
}
