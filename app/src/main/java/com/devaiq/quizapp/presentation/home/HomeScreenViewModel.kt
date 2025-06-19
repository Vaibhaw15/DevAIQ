package com.devaiq.quizapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.devaiq.quizapp.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.devaiq.quizapp.domain.model.Subject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository : SubjectRepository
) : ViewModel() {

    var subjects by mutableStateOf<List<Subject>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadSubjects()
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            isLoading = true
            subjects = repository.getSubjects()
            isLoading = false
        }
    }
}