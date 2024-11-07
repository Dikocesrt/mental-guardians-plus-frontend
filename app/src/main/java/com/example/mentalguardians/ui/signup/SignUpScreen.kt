package com.example.mentalguardians.ui.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mentalguardians.ui.theme.poppinsFontFamily

@Composable
fun SignUpScreen(navigateToLoginScreen : () -> Unit, navigateToMainScreen: () -> Unit, signUpViewModel: SignUpViewModel){
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "Daftar Akun",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color(0xFF002055)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Silahkan masukkan data diri anda untuk\nmembuat akun",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF868D95)
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = signUpViewModel.signUpUIState.firstName,
                    onValueChange = {
                        signUpViewModel.onFirstNameChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    label = {
                        Text(
                            text = "Masukkan nama depan",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF848A94)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = signUpViewModel.signUpUIState.lastName,
                    onValueChange = {
                        signUpViewModel.onLastNameChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    label = {
                        Text(
                            text = "Masukkan nama belakang",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF848A94)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = signUpViewModel.signUpUIState.email,
                    onValueChange = {
                        signUpViewModel.onEmailChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    label = {
                        Text(
                            text = "Masukkan email",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF848A94)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = signUpViewModel.signUpUIState.password,
                    onValueChange = {
                        signUpViewModel.onPasswordChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    label = {
                        Text(
                            text = "Masukkan kata sandi",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xFF848A94)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {signUpViewModel.togglePasswordVisibility()}) {
                            Icon(
                                imageVector = if (signUpViewModel.signUpUIState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "View/Hide Password"
                            )
                        }
                    },
                    visualTransformation = if (signUpViewModel.signUpUIState.isPasswordVisible) {
                        VisualTransformation.None
                    }else{
                        PasswordVisualTransformation()
                    }
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = {
                        signUpViewModel.registerUser(
                            onSuccess = {
                                navigateToMainScreen()
                            },
                            onError = {message->
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3580FF)
                    )
                ) {
                    Text(
                        text = "Daftar",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sudah Punya Akun?",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF868D95)
                )
                TextButton(
                    onClick = { navigateToLoginScreen() },
                    modifier = Modifier.padding(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Masuk",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF3580FF)
                    )
                }
            }
        }
        if (signUpViewModel.signUpUIState.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}