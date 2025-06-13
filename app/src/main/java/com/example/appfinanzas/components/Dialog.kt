package com.example.appfinanzas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogoAgregarGasto(onDismiss: () -> Unit, onAdd: (String, String) -> Unit) {
    var expenseAmount by remember { mutableStateOf("") }
    var expenseCategory by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Gasto") },
        text = {
            Column {
                OutlinedTextField(
                    value = expenseAmount,
                    onValueChange = { expenseAmount = it },
                    label = { Text("Monto") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = expenseCategory,
                    onValueChange = { expenseCategory = it },
                    label = { Text("Categoría") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(expenseAmount, expenseCategory) },
                enabled = expenseAmount.isNotBlank() && expenseCategory.isNotBlank()
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
