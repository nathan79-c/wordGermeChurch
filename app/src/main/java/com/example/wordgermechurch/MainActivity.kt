package com.example.wordgermechurch


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordgermechurch.ui.PreviewApp
import com.example.wordgermechurch.ui.theme.WordGermeChurchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordGermeChurchTheme {

                  GreetingPreview()

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordGermeChurchTheme {
        PreviewApp()
    }
}