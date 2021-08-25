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

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.samples.eventsplayground.ui.theme.EventsPlaygroundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class OnboardingActivity : AppCompatActivity() {

    val viewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsPlaygroundTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {

                        val shouldSkip by viewModel.isOnboardingCompleted.collectAsState()
                        LaunchedEffect(shouldSkip) {
                            if (shouldSkip) {
                                navController.navigate("main")
                            }
                        }
                        Greeting("Skip onboarding") {
                            viewModel.skip()
                        }
                    }
                    composable("main") {
                        // If kept in the back stack, this doesn't receive a change so when pressing
                        // back from the login activity, it works correctly.
                        // However, if you rotate the screen in the log in activity, this Onboarding
                        // activity is recreated when coming back to it, starting the LoginActivity
                        // again.
                        val shouldLogIn by viewModel.isLogInShown.collectAsState()
                        LaunchedEffect(shouldLogIn) {
                            if (shouldLogIn) {
                                // We can't reset this the same way we did viewModel.skip():

                                //viewModel.loginInitiated()  // <-- this cancels this coroutine
                                // because it changes shouldLogIn, so it's racy. It can be made
                                // reliable if you do:
                                delay(200)
                                if (!isActive) return@LaunchedEffect

                                startActivity(
                                    Intent(this@OnboardingActivity, LoginActivity::class.java)
                                )
                            }
                        }
                        BackHandler {
                            this@OnboardingActivity.finish()
                        }
                        Text("Main")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(text: String, onClick: () -> Unit = {}) {

    Surface(color = MaterialTheme.colors.background) {
        Button(onClick = onClick) {
            Text(text = text)
        }
    }
}
