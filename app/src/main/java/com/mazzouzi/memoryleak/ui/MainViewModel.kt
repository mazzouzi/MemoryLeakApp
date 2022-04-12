package com.mazzouzi.memoryleak.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _scenarioPlayedState = MutableSharedFlow<ScenarioEnum>()
    val scenarioPlayedState = _scenarioPlayedState.asSharedFlow()

    fun onScenarioSelected(scenario: ScenarioEnum) {
        viewModelScope.launch {
            /**
             * We wait 1 second before processing the event to let the material button
             * complete its ripple animation in order to avoid the fragment view leak.
             *
             * issue : https://issuetracker.google.com/issues/212993949
             */
            delay(1000)
            _scenarioPlayedState.emit(scenario)
        }
    }
}

enum class ScenarioEnum {
    BACKGROUND_LEAK,
    BACKGROUND_SOLUTION,
    SINGLETON_LEAK,
    SINGLETON_SOLUTION,
    ANONYMOUS_LEAK,
    ANONYMOUS_SOLUTION,
    ANDROID_API_LEAK,
    ANDROID_API_SOLUTION,
    BASE_FRAGMENT_LEAK,
    BASE_FRAGMENT_SOLUTION
}