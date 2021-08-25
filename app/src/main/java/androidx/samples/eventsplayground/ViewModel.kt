/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.samples.eventsplayground

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class OnboardingViewModel : ViewModel() {

    val shouldShowOnboarding = MutableStateFlow(false)
    val shouldLogIn = MutableStateFlow(true) // Coming from an auth manager or something.

    fun skip() {
        shouldShowOnboarding.value = true
    }

}
class LoginViewModel : ViewModel() {

    val shouldShowOnboarding = MutableStateFlow(false)

    fun skip() {
        shouldShowOnboarding.value = true
    }

}
