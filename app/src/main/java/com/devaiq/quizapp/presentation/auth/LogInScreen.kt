package com.devaiq.quizapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devaiq.quizapp.presentation.navigation.Screen

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isLoading = viewModel.isLoading
    val loginError = viewModel.loginError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)

    ) {
        // Back Button + Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding( vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Log In",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 12.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.5f))

        }




        Spacer(modifier = Modifier.height(24.dp))


        // Email Field
        CustomTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = "Email",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        CustomTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "Password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )


        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ){
            TextButton(onClick = {
                navController.navigate("forget")
            }) {
            Text("Forget Password?", color = Color.White)
            }
        }

       // Spacer(modifier = Modifier.height(12.dp))

        // Submit Button
        Button(
            onClick = {
                viewModel.login(
                    onSuccess = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onFailure = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                )
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDDE9F8),
                contentColor = Color.Black
            )

        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        loginError?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text("New User Sign Up", color = Color.White)
            }
        }
    }
}

