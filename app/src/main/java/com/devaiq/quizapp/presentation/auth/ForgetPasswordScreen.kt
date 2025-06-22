package com.devaiq.quizapp.presentation.auth


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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
                text = "Forgot Password",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 12.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.5f))

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Reset your password",
            color = Color.White,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Enter the email associated with your account and we'll send an email with instructions to reset your password.",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = "Email",
            keyboardType = KeyboardType.Email,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.sendResetLink(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Reset link sent! Check your email.",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    },
                    onFailure = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                )
            },
            enabled = !viewModel.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDDE9F8),
                contentColor = Color.Black
            )
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFDDE9F8),
                )
            } else {
                Text("Send Reset Instructions")
            }
        }
    }
}
