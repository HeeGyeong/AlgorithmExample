package com.example.algorithmexample.algorithm.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.algorithmexample.ui.AlgorithmMainHeader
import com.example.algorithmexample.util.measureExecutionTime

/**
 * 와인 테이스팅 문제
 *
 * https://www.acmicpc.net/problem/2156
 */
@Composable
fun WineTastingUI() {
    var n by remember { mutableStateOf("") }
    var wineInputs by remember { mutableStateOf("") }
    var result1 by remember { mutableStateOf("") }
    var result2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "포도주 시식",
            algorithmName = "Dynamic Programming",
            algorithmDescription = "연속으로 3잔을 마실 수 없을 때, 최대로 마실 수 있는 포도주의 양 구하기",
        )

        TextField(
            value = n,
            onValueChange = { n = it },
            label = { Text("포도주 잔의 개수를 입력하세요") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = wineInputs,
            onValueChange = { wineInputs = it },
            label = { Text("포도주의 양을 공백으로 구분하여 입력하세요") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val size = n.toIntOrNull() ?: 6
            val wines = if (wineInputs.isBlank()) {
                listOf(6, 10, 13, 9, 8, 1)
            } else {
                wineInputs.split(" ").mapNotNull { it.toIntOrNull() }
            }

            if (wines.size == size) {
                val (funResult, elapsedTime) = measureExecutionTime {
                    maxWine(size, wines)
                }
                result1 = "최대로 마실 수 있는 포도주의 양: $funResult (수행 시간 ${elapsedTime}ms)"
            }
        }) {
            Text("dp로 계산하기")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (result1.isNotEmpty()) {
            Text(result1)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val size = n.toIntOrNull() ?: 6
            val wines = if (wineInputs.isBlank()) {
                listOf(6, 10, 13, 9, 8, 1)
            } else {
                wineInputs.split(" ").mapNotNull { it.toIntOrNull() }
            }

            if (wines.size == size) {
                val (funResult, elapsedTime) = measureExecutionTime {
                    recursiveHelper(wines, 0, 0, 0)
                }
                result2 = "최대로 마실 수 있는 포도주의 양: $funResult (수행 시간 ${elapsedTime}ms)"
            }
        }) {
            Text("backtracking으로 계산하기")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (result2.isNotEmpty()) {
            Text(result2)
        }
    }
}

/**
 * 포도주 잔을 선택하여 최대로 마실 수 있는 포도주의 양을 계산하는 함수
 * dp[i]는 i번째 잔까지 고려했을 때 마실 수 있는 포도주의 최대 양을 저장
 *
 * 제약 조건: 연속으로 3잔을 마실 수 없음
 * 시간 복잡도: O(N) - N은 포도주 잔의 개수
 * 공간 복잡도: O(N) - dp 배열 저장용
 */
fun maxWine(n: Int, wine: List<Int>): Int {
    if (n == 1) return wine[0]
    if (n == 2) return wine[0] + wine[1]

    val dp = IntArray(n)
    dp[0] = wine[0]
    dp[1] = wine[0] + wine[1]
    dp[2] = maxOf(wine[0] + wine[1], wine[0] + wine[2], wine[1] + wine[2])

    for (index in 3 until n) {
        dp[index] =
            maxOf(
                dp[index - 1],
                dp[index - 2] + wine[index],
                dp[index - 3] + wine[index - 1] + wine[index]
            )
    }

    return dp[n - 1]
}

/**
 * 재귀와 백트래킹을 사용한 해결 방식
 * 각 위치에서 와인을 선택하거나 선택하지 않는 모든 경우의 수를 탐색
 *
 * 시간 복잡도: O(2^N) - 각 위치에서 2가지 선택(마시거나 안마시거나)
 * 공간 복잡도: O(N) - 재귀 호출 스택
 */
private fun recursiveHelper(
    wine: List<Int>,
    index: Int,
    consecutive: Int,
    sum: Int
): Int {
    if (index >= wine.size) return sum

    // 현재 와인을 선택하지 않는 경우
    val skipWine = recursiveHelper(wine, index + 1, 0, sum)

    // 연속 3잔을 마실 수 없음
    if (consecutive == 2) return skipWine

    // 현재 와인을 선택하는 경우
    val takeWine = recursiveHelper(wine, index + 1, consecutive + 1, sum + wine[index])

    return maxOf(skipWine, takeWine)
}

@Preview(showBackground = true)
@Composable
fun PreviewTwoSumAlgorithmUI() {
    WineTastingUI()
}
