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
                val userRepository = remember { UserRepository(context) } // 자바 클래스 사용

                var currentScreen by remember { mutableStateOf("main") }
                var loggedInEmail by remember { mutableStateOf<String?>(null) }

                when (currentScreen) {
                    "main" -> MainScreen(
                        onCheckRecycle = { /* ... */ },
                        onNavigateToSignUp = { currentScreen = "signup" },
                        onNavigateToLogin = {
                            if (loggedInEmail == null) currentScreen = "login"
                            else currentScreen = "profile"
                        }
                    )
                    "login" -> LoginScreen(
                        // UI는 그대로 두고, 기능만 연결
                        onLoginClicked = { email, password ->
                            if (userRepository.login(email, password)) {
                                loggedInEmail = email
                                currentScreen = "profile"
                            } else {
                                // 로그인 실패 메시지 띄우기 등
                            }
                        },
                        onSignUpClick = { currentScreen = "signup" }
                    )
                    "signup" -> SignUpScreen(
                        onSignUpComplete = { email, password, name, gender, birthDate ->
                            if (userRepository.signUp(email, password, name, gender, birthDate)) {
                                loggedInEmail = email
                                currentScreen = "profile"
                            } else {
                                // 회원가입 실패 처리
                            }
                        }
                    )
                    "profile" -> ProfileScreen(
                        onNextClicked = { /* 필요시 구현 */ },
                        onBackClicked = {
                            loggedInEmail = null
                            currentScreen = "main"
                        }
                    )
                }
            }
        }
    }
}