package com.devaiq.quizapp.presentation.level

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devaiq.quizapp.domain.model.Difficulty
import com.devaiq.quizapp.domain.repository.DifficultyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DifficultyScreenViewModel @Inject constructor(
    private val repository: DifficultyRepository
) : ViewModel() {

    var levels by mutableStateOf<List<Difficulty>>(emptyList())
    private set

    var isLoading by mutableStateOf(false)

    fun loadLevels(subjectId: String) {
        viewModelScope.launch {
            isLoading = true
            levels = repository.getDifficulties(subjectId)
            isLoading = false
        }
    }
}