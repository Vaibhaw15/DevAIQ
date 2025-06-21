import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.devaiq.quizapp.presentation.auth.CustomTextField
import com.devaiq.quizapp.presentation.navigation.Screen
import com.devaiq.quizapp.presentation.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    rootNavController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val name = viewModel.name
    val email = viewModel.email
    val newPassword = viewModel.newPassword
    val currentPassword = viewModel.currentPassword
    val message = viewModel.message
    val isLoading = viewModel.isLoading
    val focusManager = LocalFocusManager.current


    Box (modifier = Modifier
            .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = remember{ MutableInteractionSource() }
        ) {
            focusManager.clearFocus()
        }
        .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Back Button + Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
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
                    text = "Profile",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(4f)
                        .padding(start = 12.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(0.5f))

            }
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            CustomTextField(
                value = name,
                onValueChange = { viewModel.name = it },
                placeholder = "Name",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = email,
                onValueChange = {},
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                enabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = currentPassword,
                onValueChange = { viewModel.currentPassword = it },
                placeholder = "Current Password",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = newPassword,
                onValueChange = { viewModel.newPassword = it },
                placeholder = "New Password (optional)",
                keyboardType = KeyboardType.Password,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.updateProfile() },
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
                Text(if (isLoading) "Updating..." else "Update Profile")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.logout {
                        rootNavController.navigate(Screen.Splash.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Logout")
            }


            message?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(it, color = if (it.contains("success", true)) Color.Green else Color.Red)
            }
        }
    }
}

