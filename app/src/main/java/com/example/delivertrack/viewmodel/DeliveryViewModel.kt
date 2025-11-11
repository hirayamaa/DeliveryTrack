package com.example.delivertrack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivertrack.data.DeliveryRecord
import com.example.delivertrack.repository.DeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * ViewModel（Hilt + StateFlow）
 */
@HiltViewModel
class DeliveryViewModel @Inject constructor(
    application: Application,
    private val repository: DeliveryRepository
) : AndroidViewModel(application) {

    // records Flow -> StateFlow for Compose
    val records: StateFlow<List<com.example.delivertrack.data.DeliveryRecord>> =
        repository.getAll()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Timer state
    private var startTime = 0L
    private var timerJob: Job? = null

    private val _elapsedMillis = MutableStateFlow(0L)
    val elapsedMillis: StateFlow<Long> = _elapsedMillis

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    // Start timer
    fun startTimer() {
        if (_isRunning.value) return
        startTime = System.currentTimeMillis()
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isRunning.value) {
                _elapsedMillis.value = System.currentTimeMillis() - startTime
                delay(1000)
            }
        }
    }

    // Stop timer and return elapsed
    fun stopTimer(): Long {
        _isRunning.value = false
        timerJob?.cancel()
        val elapsed = _elapsedMillis.value
        _elapsedMillis.value = 0L
        return elapsed
    }

    // Save record to DB
    fun saveRecord(durationMillis: Long, earnings: Double, note: String?) {
        viewModelScope.launch {
            val record = DeliveryRecord(
                dateIso = LocalDate.now().toString(),
                durationMillis = durationMillis,
                earnings = earnings,
                note = note
            )
            repository.insert(record)
        }
    }
}