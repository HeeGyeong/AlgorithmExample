package com.example.algorithmexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.algorithmexample.ui.AlgorithmMainHeader
import com.example.algorithmexample.ui.theme.AlgorithmExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlgorithmExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "Algorithm Example",
            algorithmName = "알고리즘 샘플 예제 입니다.",
            algorithmDescription = "해당 repository에서 확인하고자 하는 알고리즘을 찾아 preview를 실행시켜 확인하시면 됩니다.",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlgorithmExampleTheme {
        Greeting()
    }
}