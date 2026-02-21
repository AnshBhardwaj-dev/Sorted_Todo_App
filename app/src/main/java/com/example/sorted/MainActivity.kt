package com.example.sorted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sorted.presentation.todo.TodoScreen
import com.example.sorted.presentation.todo.TodoViewModel
import com.example.sorted.ui.theme.SortedTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TodoViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            enableEdgeToEdge()

            SortedTheme(
                darkTheme = isDarkTheme
            ) {
                val backgroundColor = MaterialTheme.colorScheme.background
                val activity = this@MainActivity

                SideEffect {

                    activity.enableEdgeToEdge(
                        statusBarStyle =
                            if (isDarkTheme)
                                SystemBarStyle.dark(backgroundColor.toArgb())
                            else
                                SystemBarStyle.light(
                                    backgroundColor.toArgb(),
                                    backgroundColor.toArgb()
                                ),

                        navigationBarStyle =
                            if (isDarkTheme)
                                SystemBarStyle.dark(backgroundColor.toArgb())
                            else
                                SystemBarStyle.light(
                                    backgroundColor.toArgb(),
                                    backgroundColor.toArgb()
                                )
                    )
                }
                TodoScreen(
                    onThemeToggle = { viewModel.toggleTheme() },
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier.systemBarsPadding(),
                    viewModel = viewModel
                )
            }
        }
    }
}