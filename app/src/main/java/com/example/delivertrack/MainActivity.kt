package com.example.delivertrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.delivertrack.ui.HistoryScreen
import com.example.delivertrack.ui.HomeScreen
import com.example.delivertrack.ui.RecordInputScreen
import com.example.delivertrack.ui.theme.DeliverTrackTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeliverTrackTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val vm: com.example.delivertrack.viewmodel.DeliveryViewModel = hiltViewModel()
                            HomeScreen(
                                viewModel = vm,
                                onNavigateToRecord = { duration ->
                                    navController.navigate("record/$duration")
                                },
                                onNavigateToHistory = { navController.navigate("history") }
                            )
                        }
                        composable("record/{duration}") { backStackEntry ->
                            val vm: com.example.delivertrack.viewmodel.DeliveryViewModel = hiltViewModel()
                            val duration = backStackEntry.arguments?.getString("duration")?.toLong() ?: 0L
                            RecordInputScreen(
                                duration = duration,
                                onSave = { earnings, note ->
                                    vm.saveRecord(duration, earnings, note)
                                    navController.popBackStack()
                                },
                                onCancel = { navController.popBackStack() }
                            )
                        }
                        composable("history") {
                            val vm: com.example.delivertrack.viewmodel.DeliveryViewModel = hiltViewModel()
                            HistoryScreen(viewModel = vm, onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}