package net.iesseveroochoa.victorsanchez.t04ejercicio3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Ejercicio3Calculadora : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

/**
 * Función principal calculadora
 */
@Preview
@Composable
fun CalculatorApp() {

    var number1 by rememberSaveable { mutableStateOf("") }
    var number2 by rememberSaveable { mutableStateOf("") }
    var selectedOperation by rememberSaveable { mutableStateOf("SUMA") }
    var result by rememberSaveable { mutableStateOf(0.0) }
    var iconId by rememberSaveable { mutableStateOf(R.drawable.sum) }
    val focusManager = LocalFocusManager.current

    // Diseño de la interfaz de usuario con padding en 16
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Calculadora",
                fontSize = 32.sp,
                modifier = Modifier.padding(16.dp)
            )

            // Número 1
            CustomOutlinedTextField(
                value = number1,
                label = "Número 1",
                onValueChange = {
                    number1 = it
                },
                onNext = {
                    focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down) // Pasa al número 2
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Número 2
            CustomOutlinedTextField(
                value = number2,
                label = "Número 2",
                onValueChange = {
                    number2 = it
                },
                onDone = {
                    focusManager.clearFocus() // Cierra el teclado
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opciones de operación
            Row {
                RadioButtonOption("SUMA", selectedOperation, onSelect = {
                    selectedOperation = it
                    calculateResult(number1, number2, selectedOperation) { newResult ->
                        result = newResult
                    }
                    iconId = R.drawable.sum
                })
                RadioButtonOption("RESTA", selectedOperation, onSelect = {
                    selectedOperation = it
                    calculateResult(number1, number2, selectedOperation) { newResult ->
                        result = newResult
                    }
                    iconId = R.drawable.resta
                })
                RadioButtonOption("MULT", selectedOperation, onSelect = {
                    selectedOperation = it
                    calculateResult(number1, number2, selectedOperation) { newResult ->
                        result = newResult
                    }
                    iconId = R.drawable.mult
                })
                RadioButtonOption("DIV", selectedOperation, onSelect = {
                    selectedOperation = it
                    calculateResult(number1, number2, selectedOperation) { newResult ->
                        result = newResult
                    }
                    iconId = R.drawable.div
                })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "RESULTADO", fontSize = 24.sp)
            Text(text = result.toString(), fontSize = 32.sp)

            // Icono de la operación seleccionada
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// Composable para el campo de texto personalizado
@Composable
fun CustomOutlinedTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = androidx.compose.ui.text.input.ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() },
            onDone = { onDone() }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

// Composable para las opciones de RadioButton
@Composable
fun RadioButtonOption(
    label: String,
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedOption == label,
            onClick = { onSelect(label) }
        )
        Text(text = label)
    }
}

// Función para calcular el resultado
fun calculateResult(
    number1: String,
    number2: String,
    operation: String,
    updateResult: (Double) -> Unit
) {
    val num1 = number1.toDoubleOrNull() ?: 0.0
    val num2 = number2.toDoubleOrNull() ?: 0.0
    val result = when (operation) {
        "SUMA" -> num1 + num2
        "RESTA" -> num1 - num2
        "MULT" -> num1 * num2
        "DIV" -> if (num2 != 0.0) num1 / num2 else 0.0
        else -> 0.0
    }
    updateResult(result)
}

