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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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
    onNextClicked: () -> Unit = {}, // 다음 화면으로 이동할 콜백
    onBackClicked: () -> Unit = {}  // 이전 화면으로 돌아갈 콜백
) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
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

        // 이름 입력
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // 성별 선택 (DropdownMenu 사용)
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("성별") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "성별 선택 아이콘",
                        tint = darkGreen,
                        modifier = Modifier.clickable { genderMenuExpanded = true }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkGreen,
                    cursorColor = darkGreen,
                    focusedLabelColor = darkGreen
                ),
                modifier = Modifier.fillMaxWidth()
            )
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

        // 생년월일 입력
        OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
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

        // 다음 버튼
        Button(
            onClick = { /*  개인정보 입력 여부 판단
                when {
                    name.isBlank() || gender.isBlank() || birthDate.isBlank() -> {
                        errorMessage = "모든 항목을 입력해주세요."
                        Log.d("ProfileScreen", errorMessage)
                    }
                    else -> {
                        errorMessage = ""
                        Log.d("ProfileScreen", "개인정보 입력 완료: 이름=$name, 성별=$gender, 생년월일=$birthDate")
                        onNextClicked()
                    }
                }
           */ },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(2.dp, darkGreen, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text("다음", style = MaterialTheme.typography.titleMedium)
        }
    }
}