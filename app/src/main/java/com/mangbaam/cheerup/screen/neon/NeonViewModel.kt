package com.mangbaam.cheerup.screen.neon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NeonViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(NeonState())
    val state = _state.asStateFlow()

    fun changeDisplayText(text: String) {
        _state.update { it.copy(displayText = text) }
    }

    fun changeSpeed(speed: Int) {
        _state.update { it.copy(speed = speed) }
    }

    fun changeTextSize(size: Int) {
        _state.update { it.copy(textSize = size) }
    }

    fun changeFontWeight(weight: Int) {
        _state.update { it.copy(fontWeight = weight) }
    }
}
