package androidx.samples.eventsplayground

import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.samples.eventsplayground.ui.theme.EventsPlaygroundTheme

class OnboardingActivity : ComponentActivity() {

    val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsPlaygroundTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {

                        val shouldSkip by viewModel.shouldShowOnboarding.collectAsState()
                        LaunchedEffect(shouldSkip) {
                            if (shouldSkip) navController.navigate("main")

                        }
                        Greeting("Onboarding") {
                            viewModel.skip()
                        }

                    }
                    composable("main") { Greeting("Main") }
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
