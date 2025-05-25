package com.example.cleanearth

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment

@Composable
fun ProfileScreen(
    onNextClicked: (String, String, String) -> Unit = { _, _, _ -> },
    onBackClicked: () -> Unit = {},
    initialName: String = "",
    initialGender: String = "",
    initialBirthDate: String = "",
    readOnly: Boolean = false
) {
    var name by remember { mutableStateOf(initialName) }
    var gender by remember { mutableStateOf(initialGender) }
    var birthDate by remember { mutableStateOf(initialBirthDate) }
    var errorMessage by remember { mutableStateOf("") }
    var genderMenuExpanded by remember { mutableStateOf(false) }

    val darkGreen = Color(0xFF4CAF50)
    val genderOptions = listOf("남성", "여성", "기타")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "개인정보 입력",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "이름, 성별, 생년월일을 입력해주세요",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { if (!readOnly) name = it },
            label = { Text("이름") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "이름 아이콘",
                    tint = darkGreen
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkGreen,
                cursorColor = darkGreen,
                focusedLabelColor = darkGreen
            ),
            enabled = !readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("성별") },
                trailingIcon = {
                    if (!readOnly) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "성별 선택 아이콘",
                            tint = darkGreen,
                            modifier = Modifier.clickable { genderMenuExpanded = true }
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkGreen,
                    cursorColor = darkGreen,
                    focusedLabelColor = darkGreen
                ),
                enabled = !readOnly,
                modifier = Modifier.fillMaxWidth()
            )
            if (!readOnly) {
                DropdownMenu(
                    expanded = genderMenuExpanded,
                    onDismissRequest = { genderMenuExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                gender = option
                                genderMenuExpanded = false
                            }
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = birthDate,
            onValueChange = { if (!readOnly) birthDate = it },
            label = { Text("생년월일 (YYYY-MM-DD)") },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "생년월일 아이콘",
                    tint = darkGreen
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkGreen,
                cursorColor = darkGreen,
                focusedLabelColor = darkGreen
            ),
            enabled = !readOnly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (!readOnly) {
            Button(
                onClick = {
                    when {
                        name.isBlank() || gender.isBlank() || birthDate.isBlank() -> {
                            errorMessage = "모든 항목을 입력해주세요."
                        }
                        else -> {
                            errorMessage = ""
                            onNextClicked(name, gender, birthDate)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("완료", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onBackClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("뒤로가기", style = MaterialTheme.typography.titleMedium, color = darkGreen)
            }
        }
    }
}