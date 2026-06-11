package com.example.auraai

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.auraai.data.model.UserProfile
import com.example.auraai.data.store.UserPreferencesStore
import com.example.auraai.ui.theme.AuraaiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesStore: UserPreferencesStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Manual test: save a profile
        lifecycleScope.launch {
            val testProfile = UserProfile(
                name = "Antonio",
                age = "25",
                phone = "123-456-7890",
                traits = listOf("Adventurous", "Tech-savvy"),
            )
            userPreferencesStore.save(testProfile)
            Log.d("MainActivity", "Test profile saved")
        }

        setContent {
            val userProfile by userPreferencesStore.profile.collectAsState(initial = UserProfile())

            LaunchedEffect(userProfile) {
                Log.d("MainActivity", "Observed profile: $userProfile")
            }

            AuraaiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuraGreeting(
                        name = userProfile.name.ifEmpty { "Android" },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AuraGreeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun AuraGreetingPreview() {
    AuraaiTheme {
        AuraGreeting("Android")
    }
}
