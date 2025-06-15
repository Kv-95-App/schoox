package kv.apps.schoox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import kv.apps.schoox.presentation.navigation.NavigationGraph
import kv.apps.schoox.presentation.theme.SchooxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchooxTheme {
                Surface {
                    NavigationGraph()
                }
            }
        }
    }
}