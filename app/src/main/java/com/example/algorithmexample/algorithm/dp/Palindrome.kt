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
import androidx.compose.ui.unit.dp
import com.example.algorithmexample.ui.AlgorithmMainHeader
import com.example.algorithmexample.util.measureExecutionTime

/**
 * 짝수 팰린드롬 문제
 *
 * https://www.acmicpc.net/problem/21925
 */
@Composable
fun PalindromeUI() {
    var input by remember { mutableStateOf("") } // default example
    var result1 by remember { mutableStateOf("") }
    var result2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "짝수 팰린드롬 분할",
            algorithmName = "DP",
            algorithmDescription = "길이가 N인 수열을 여러 개의 짝수 팰린드롬으로 나누었을 때, 가능한 최대 팰린드롬 개수를 구하는 문제",
        )

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("팰린드롬을 확인할 숫자를 공백으로 구분하여 입력해주세요.") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val inputText = if (input.trim().isEmpty()) {
                "1 1 5 6 7 7 6 5 5 5"
            } else {
                input.trim()
            }
            val numbers = inputText.split(" ").mapNotNull { it.toIntOrNull() }.toIntArray()
            val (result, elapsedTime) = measureExecutionTime {
                checkPalindromeBackward(numbers, numbers.size)
            }
            result1 =
                "최대 팰린드롬 개수: $result (수행시간: ${elapsedTime}ms)"
        }) {
            Text("Palindrome Backward")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result1)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val startTime = System.nanoTime()
            val inputText = if (input.trim().isEmpty()) {
                "1 1 5 6 7 7 6 5 5 5"
            } else {
                input.trim()
            }
            val numbers = inputText.split(" ").mapNotNull { it.toIntOrNull() }.toIntArray()
            val palindromeCount = checkPalindromeForward(numbers, numbers.size)
            val endTime = System.nanoTime()
            val executionTime = (endTime - startTime) / 1_000_000.0 // 밀리초로 변환
            result2 =
                "최대 팰린드롬 개수: $palindromeCount (수행시간: ${String.format("%.3f", executionTime)}ms)"
        }) {
            Text("Palindrome Forward")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result2)
    }
}

/**
 * 주어진 배열을 뒤에서부터 검사하며 최대 짝수 팰린드롬 분할 개수를 구하는 함수
 * dp[i]는 0부터 i까지의 부분 배열에서 가능한 최대 짝수 팰린드롬 분할 개수를 저장
 *
 * 시간 복잡도: O(N^3) - N은 배열 크기, isPalindrome 검사에 O(N)
 * 공간 복잡도: O(N) - dp 배열 저장용
 */
fun checkPalindromeBackward(defaultArray: IntArray, size: Int): Int {
    val dp = IntArray(size + 1) { -1 }
    dp[0] = 0

    for (i in 2..size) {
        for (j in i - 2 downTo 0 step 2) {
            if (isPalindrome(defaultArray, j, i - 1)) {
                if (dp[j] != -1) {
                    dp[i] = maxOf(dp[i], dp[j] + 1)
                }
            }
        }
    }

    return dp[size]
}

/**
 * 주어진 배열을 앞에서부터 검사하며 최대 짝수 팰린드롬 분할 개수를 구하는 함수
 * checkPalindromeBackward와 동일한 결과를 반환하지만 반복문의 진행 방향이 반대
 *
 * 시간 복잡도: O(N^3) - N은 배열 크기, isPalindrome 검사에 O(N)
 * 공간 복잡도: O(N) - dp 배열 저장용
 */
fun checkPalindromeForward(defaultArray: IntArray, size: Int): Int {
    val dp = IntArray(size + 1) { -1 }
    dp[0] = 0

    for (i in 2..size) {
        for (j in 0..(i - 2) step 2) {
            if (isPalindrome(defaultArray, (i - 2) - j, i - 1)) {
                if (dp[(i - 2) - j] != -1) {
                    dp[i] = maxOf(dp[i], dp[(i - 2) - j] + 1)
                }
            }
        }
    }

    return dp[size]
}

fun isPalindrome(checkArray: IntArray, start: Int, end: Int): Boolean {
    var left = start
    var right = end

    while (left < right) {
        if (checkArray[left] != checkArray[right]) return false
        left++
        right--
    }

    return true
}