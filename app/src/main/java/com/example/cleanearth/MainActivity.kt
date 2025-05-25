package com.example.cleanearth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.cleanearth.ui.MainScreen
import com.example.cleanearth.ui.theme.CleanEarthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanEarthTheme {
                val context = LocalContext.current
                val userRepository = remember { UserRepository(context) }

                var currentScreen by remember { mutableStateOf("main") }
                var signUpEmail by remember { mutableStateOf("") }
                var signUpPassword by remember { mutableStateOf("") }
                // 회원정보 임시저장
                var name by remember { mutableStateOf("") }
                var gender by remember { mutableStateOf("") }
                var birthDate by remember { mutableStateOf("") }
                // 로그인상태
                var loggedInEmail by remember { mutableStateOf<String?>(null) }
                var loggedInProfile by remember { mutableStateOf<UserProfile?>(null) }

                when (currentScreen) {
                    "main" -> MainScreen(
                        onCheckRecycle = { /* ... */ },
                        onNavigateToSignUp = { currentScreen = "signup" },
                        onNavigateToLogin = { currentScreen = "login" }
                    )
                    "signup" -> SignUpScreen(
                        onNextClicked = { email, password ->
                            signUpEmail = email
                            signUpPassword = password
                            currentScreen = "profileInput"
                        }
                    )
                    "profileInput" -> ProfileScreen(
                        onNextClicked = { inputName, inputGender, inputBirthDate ->
                            // 회원가입 → DB에 저장 (이메일=키)
                            userRepository.registerAll(
                                email = signUpEmail,
                                password = signUpPassword,
                                name = inputName,
                                gender = inputGender,
                                birthDate = inputBirthDate
                            )
                            // 입력값 초기화
                            name = ""
                            gender = ""
                            birthDate = ""
                            signUpEmail = ""
                            signUpPassword = ""
                            // 로그인 창 이동
                            currentScreen = "login"
                        },
                        onBackClicked = { currentScreen = "signup" }
                    )
                    "login" -> LoginScreen(
                        onLoginClicked = { email, password ->
                            if (userRepository.login(email, password)) {
                                loggedInEmail = email
                                // 로그인 시, DB에서 개인정보 불러오기
                                loggedInProfile = userRepository.getProfile(email)
                                currentScreen = "profile"
                            }
                        },
                        onSignUpClick = { currentScreen = "signup" }
                    )
                    "profile" -> ProfileScreen(
                        onNextClicked = { _, _, _ -> },
                        onBackClicked = {
                            loggedInEmail = null
                            loggedInProfile = null
                            currentScreen = "main"
                        },
                        initialName = loggedInProfile?.name ?: "",
                        initialGender = loggedInProfile?.gender ?: "",
                        initialBirthDate = loggedInProfile?.birthDate ?: "",
                        readOnly = true
                    )
                }
            }
        }
    }
}