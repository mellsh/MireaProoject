package com.example.cleanearth

import android.database.Cursor
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

                // 화면 상태 관리
                var currentScreen by remember { mutableStateOf("main") }
                var loggedInEmail by remember { mutableStateOf<String?>(null) }

                // 로그인 상태 관리
                var loginEmail by remember { mutableStateOf("") }
                var loginPassword by remember { mutableStateOf("") }
                var loginError by remember { mutableStateOf("") }

                // 회원가입 상태 관리
                var signUpEmail by remember { mutableStateOf("") }
                var signUpPassword by remember { mutableStateOf("") }
                var signUpError by remember { mutableStateOf("") }

                // 프로필 입력/조회 상태 관리
                var profileName by remember { mutableStateOf("") }
                var profileGender by remember { mutableStateOf("") }
                var profileBirthDate by remember { mutableStateOf("") }
                var profileError by remember { mutableStateOf("") }

                when (currentScreen) {
                    "main" -> MainScreen(
                        onNavigateToProfile = {
                            if (loggedInEmail == null) {
                                // 로그인 되어있지 않으면 로그인 화면으로 이동
                                currentScreen = "login"
                                loginEmail = ""
                                loginPassword = ""
                                loginError = ""
                            } else {
                                // 이미 로그인 상태면 프로필 바로 보여주기
                                val cursor: Cursor = userRepository.getProfile(loggedInEmail!!)
                                if (cursor.moveToFirst()) {
                                    profileName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                                    profileGender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                                    profileBirthDate = cursor.getString(cursor.getColumnIndexOrThrow("birthDate"))
                                }
                                cursor.close()
                                currentScreen = "profile"
                            }
                        },
                        onNavigateToSignUp = {
                            currentScreen = "signup"
                            signUpEmail = ""
                            signUpPassword = ""
                            signUpError = ""
                        }
                    )

                    "login" -> LoginScreen(
                        email = loginEmail,
                        password = loginPassword,
                        errorMessage = loginError,
                        onEmailChange = { loginEmail = it },
                        onPasswordChange = { loginPassword = it },
                        onLoginClicked = { email, password ->
                            if (userRepository.login(email, password)) {
                                loggedInEmail = email
                                // 프로필 정보 불러오기
                                val cursor: Cursor = userRepository.getProfile(email)
                                if (cursor.moveToFirst()) {
                                    profileName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                                    profileGender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                                    profileBirthDate = cursor.getString(cursor.getColumnIndexOrThrow("birthDate"))
                                }
                                cursor.close()
                                loginError = ""
                                currentScreen = "profile"
                            } else {
                                loginError = "이메일 또는 비밀번호가 올바르지 않습니다."
                            }
                        },
                        onBackClicked = { currentScreen = "main" }
                    )

                    "signup" -> SignUpScreen(
                        email = signUpEmail,
                        password = signUpPassword,
                        errorMessage = signUpError,
                        onEmailChange = { signUpEmail = it },
                        onPasswordChange = { signUpPassword = it },
                        onNextClicked = { email, password, confirmPassword ->
                            when {
                                email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                                    signUpError = "모든 항목을 입력해주세요."
                                password != confirmPassword ->
                                    signUpError = "비밀번호가 일치하지 않습니다."
                                userRepository.login(email, password) ->
                                    signUpError = "이미 존재하는 이메일입니다."
                                else -> {
                                    signUpError = ""
                                    signUpEmail = email
                                    signUpPassword = password
                                    // 회원정보 입력(프로필) 화면으로 이동
                                    profileName = ""
                                    profileGender = ""
                                    profileBirthDate = ""
                                    profileError = ""
                                    currentScreen = "profileInput"
                                }
                            }
                        },
                        onBackClicked = { currentScreen = "main" }
                    )

                    "profileInput" -> ProfileScreen(
                        name = profileName,
                        gender = profileGender,
                        birthDate = profileBirthDate,
                        errorMessage = profileError,
                        onNameChange = { profileName = it },
                        onGenderChange = { profileGender = it },
                        onBirthDateChange = { profileBirthDate = it },
                        onNextClicked = { name, gender, birthDate ->
                            when {
                                name.isBlank() || gender.isBlank() || birthDate.isBlank() -> {
                                    profileError = "모든 항목을 입력해주세요."
                                }
                                else -> {
                                    val success = userRepository.signUp(
                                        signUpEmail,
                                        signUpPassword,
                                        name,
                                        gender,
                                        birthDate
                                    )
                                    if (success) {
                                        profileError = ""
                                        // 회원가입 성공 → 로그인 화면으로
                                        currentScreen = "login"
                                    } else {
                                        profileError = "회원가입에 실패했습니다."
                                    }
                                }
                            }
                        },
                        onBackClicked = { currentScreen = "signup" }
                    )

                    "profile" -> ProfileScreen(
                        name = profileName,
                        gender = profileGender,
                        birthDate = profileBirthDate,
                        errorMessage = "",
                        readOnly = true,
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