package com.example.algorithmexample.algorithm.backtracking

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

@Composable
fun NandMProblemUI() {
    var inputN by remember { mutableStateOf("") }
    var inputM by remember { mutableStateOf("") }
    var inputArray by remember { mutableStateOf("") }

    var output1 by remember { mutableStateOf("") }
    var output2 by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "N and M",
            algorithmName = "back tracking",
            algorithmDescription = "주어진 숫자와 수열에서, 중복되지 않으면서 오름차순인 수열을 구하는 문제.",
        )

        TextField(
            value = inputN,
            onValueChange = { inputN = it },
            label = { Text("배열의 길이를 입력하세요.(4)") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = inputM,
            onValueChange = { inputM = it },
            label = { Text("선택할 배열의 길이를 입력하세요.(2)") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = inputArray,
            onValueChange = {
                inputArray = it
                errorMessage = ""
            },
            label = { Text("N개의 배열을 입력하세요. (9, 7, 9, 1)") },
            isError = errorMessage.isNotEmpty(),
            supportingText = {
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val n = inputN.toIntOrNull() ?: 4
            val m = inputM.toIntOrNull() ?: 2
            val numbers = if (inputArray.isBlank()) {
                listOf(9, 7, 9, 1).sorted()
            } else {
                inputArray.split(",").mapNotNull { it.trim().toIntOrNull() }.sorted()
            }

            if (numbers.size < n) {
                errorMessage = "입력된 숫자가 N보다 작습니다. N개의 숫자를 입력해주세요."
                return@Button
            }

            output1 = findSequences(m, numbers)
        }) {
            Text("BackTracking 결과 보기")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = output1)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val n = inputN.toIntOrNull() ?: 4
            val m = inputM.toIntOrNull() ?: 2
            val numbers = if (inputArray.isBlank()) {
                listOf(9, 7, 9, 1).sorted()
            } else {
                inputArray.split(",").mapNotNull { it.trim().toIntOrNull() }.sorted()
            }

            if (numbers.size < n) {
                errorMessage = "입력된 숫자가 N보다 작습니다. N개의 숫자를 입력해주세요."
                return@Button
            }

            output2 = findSequencesCombinations(m, numbers)
        }) {
            Text("재귀 함수 결과 보기")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = output2)
    }
}

/**
 * Backtracking을 사용하여 문제를 해결합니다.
 * 모든 문자열을 순회하며 재귀 함수를 호출하여 조합을 생성합니다.
 * 중복된 index를 사용하지 않기 위해 중복 방지 로직을 추가합니다.
 *
 * numbers에 들어가는 List는 반드시 오름차순 정렬이 되어있어야 합니다.
 * 저장하는 문자열이 출력해야 하는 길이와 일치하면 해당 문자열을 results에 추가합니다.
 *
 * 시간 복잡도와 공간 복잡도는 backtracking이기 때문에 고려하지 않습니다.
 */
fun findSequences(m: Int, numbers: List<Int>): String {
    val results = mutableListOf<List<Int>>()
    val combination = mutableListOf<Int>()

    fun generateCombinations(start: Int) {
        if (combination.size == m) {
            results.add(combination.toList())
            return
        }

        // start부터 numbers 배열 끝까지 순회
        for (i in start until numbers.size) {
            // 중복된 숫자 건너뛰기
            if (i > start && numbers[i] == numbers[i - 1]) continue

            combination.add(numbers[i]) // 현재 숫자 선택
            generateCombinations(i + 1) // 다음 숫자 선택을 위해 재귀 호출 (i+1로 오름차순 보장)
            combination.removeAt(combination.size - 1) // 백트래킹: 선택 취소
        }
    }

    generateCombinations(0) // 0부터 시작하여 조합 생성

    // 결과를 문자열로 변환
    return results.joinToString("\n") { sequence ->
        sequence.joinToString(" ")
    }
}

/**
 * 결과 값 반환 함수
 * numbers를 정렬하여 오름차순을 보장
 * combinations() 확장함수로 모든 조합을 생성
 * 결과를 문자열 형태로 변환하여 반환
 */
fun findSequencesCombinations(m: Int, numbers: List<Int>): String {
    return numbers.sorted()
        .combinations(m)
        .joinToString("\n") { it.joinToString(" ") }
}

/**
 * 재귀 함수로 처리
 * head: 현재 리스트의 첫 번째 요소
 * tail: 첫 번째 요소를 제외한 나머지 리스트
 *
 * 시간 복잡도: O(2^n) - 모든 가능한 조합을 생성
 * 공간 복잡도: O(n) - 재귀 호출 스택 깊이
 */
fun <T> List<T>.combinations(m: Int): List<List<T>> {
    if (m == 0) return listOf(emptyList())
    if (isEmpty()) return emptyList()

    val result = mutableListOf<List<T>>()
    val head = first()
    val tail = drop(1)

    tail.combinations(m - 1) // tail에 들어간 배열을 하나의 아이템으로 나누어 줌.
        .forEach { result.add(listOf(head) + it) } // 하나의 아이템을 더해 결과적으로 size가 m이 된다.

    // 첫 번째 아이템(head)을 뺀 나머지 아이템을 기반으로 재귀호출한다.
    result.addAll(tail.combinations(m))

    return result.distinct() // 중복된 결과를 제거한다.
}

@Preview(showBackground = true)
@Composable
fun PreviewNandMUI() {
    NandMProblemUI()
}