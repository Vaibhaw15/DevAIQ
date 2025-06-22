package com.devaiq.quizapp.presentation.performance

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devaiq.quizapp.domain.model.PerformanceModel
import com.devaiq.quizapp.utils.subjectColors
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun PerformanceScreen(
    navController: NavController,
    viewModel: PerformanceViewModel = hiltViewModel()
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    LaunchedEffect(Unit) {
        viewModel.loadPerformance(userId)
    }

    val list = viewModel.performanceList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
                text = "Performance",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 12.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.5f))

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(list) { result: PerformanceModel ->
                ResultCard(result)
            }
        }
    }
}


@Composable
fun ResultCard(result: PerformanceModel) {
    val percentage = (result.correct.toFloat() / result.total * 100).toInt()
    fun getSubjectColor(subjectId: String): Color {
        val index = (subjectId.hashCode().absoluteValue) % subjectColors.size
        return subjectColors[index]
    }
    val color = getSubjectColor(result.subjectId)
    val tagColor = when (result.difficulty.lowercase()) {
        "easy" -> Color(0xFFFFD37D)
        "medium" -> Color(0xFF81E1F5)
        "hard" -> Color(0xFFDDA8FF)
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color.copy(alpha = 0.1f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.subjectId.first().uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        text = result.subjectId.replaceFirstChar { it.uppercase() },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                tagColor.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = result.difficulty.replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = result.correct.toFloat() / result.total,
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("${result.correct} / ${result.total}")
                Text("$percentage%", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault()).format(result.timestamp),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
