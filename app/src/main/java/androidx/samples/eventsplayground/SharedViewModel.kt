package androidx.samples.eventsplayground

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SharedViewModel : ViewModel() {

    val shouldShowOnboarding = MutableStateFlow(false)

    fun skip() {
        shouldShowOnboarding.value = true
    }

}
