package com.example.algorithmexample.algorithm.hash

import android.util.Log
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
fun TwoSumUI() {
    var input by remember { mutableStateOf("") }
    var targetInput by remember { mutableStateOf("") }
    var result1 by remember { mutableStateOf("") }
    var result2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "TwoSum Problem",
            algorithmName = "Two Sum",
            algorithmDescription = "주어진 배열에서 두 수의 합이 목표값(target)이 되는 두 수의 인덱스를 찾는 문제",
        )

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter numbers separated by commas (2, 11, 7, 15)") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = targetInput,
            onValueChange = { targetInput = it },
            label = { Text("Enter target number (9)") }
        )

        Button(onClick = {
            result1 = runAlgorithm(::findTwoSum, input, targetInput)
        }) {
            Text("Hash Run")
        }

        Text(text = "Output: $result1")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            result2 = runAlgorithm(::findTwoSumBruteForce, input, targetInput)
        }) {
            Text("BruteForce Run")
        }

        Text(text = "Output: $result2")
    }
}

fun runAlgorithm(
    algorithm: (List<Int>, Int) -> List<Int>,
    input: String,
    targetInput: String
): String {
    val nums = input.ifEmpty { "11, 15, 15, 1, 4, 59, 2, 112, 343, 123, 10, 7" }.split(",")
        .map { it.trim().toInt() }
    val target = targetInput.ifEmpty { "9" }.toIntOrNull() ?: 0
    val startTime = System.currentTimeMillis()
    val result = algorithm(nums, target).toString()
    val endTime = System.currentTimeMillis()
    return "$result (Time: ${endTime - startTime} ms)"
}

/**
 * 해시 맵을 사용하여 Two Sum 문제를 해결합니다.
 * 리스트의 숫자를 순회하면서 각 숫자에 대해 목표 값과의 목표 값을 계산합니다.
 * 목표가 맵에 존재하면 두 숫자의 인덱스를 반환합니다.
 * 그렇지 않으면 숫자와 그 인덱스를 맵에 저장합니다.
 *
 * 시간 복잡도: O(n) - 리스트를 한 번 순회합니다.
 * 공간 복잡도: O(n) - 맵에 최대 n개의 요소를 저장합니다.
 * 장점: O(n) 시간 복잡도로 인해 큰 리스트에 대해 효율적입니다.
 * 단점: 해시 맵을 위한 추가 공간이 필요합니다.
 */
fun findTwoSum(nums: List<Int>, target: Int): List<Int> {
    val map = mutableMapOf<Int, Int>()
    for ((index, num) in nums.withIndex()) {
        val complement = target - num
        Log.d("TwoSum", "Checking number: $num, Complement: $complement, Map: $map")
        if (map.containsKey(complement)) {
            Log.d("TwoSum", "Found pair: (${map[complement]}, $index)")
            return listOf(map[complement]!!, index)
        }
        map[num] = index
    }
    Log.d("TwoSum", "No pair found")
    return emptyList()
}

/**
 * 브루트 포스를 사용하여 Two Sum 문제를 해결합니다.
 * 목표 값에 합이 되는 쌍을 찾기 위해 모든 가능한 숫자 쌍을 검사합니다.
 * 첫 번째로 찾은 쌍의 인덱스를 반환합니다.
 *
 * 시간 복잡도: O(n^2) - 각 숫자 쌍을 검사합니다.
 * 공간 복잡도: O(1) - 추가 공간을 사용하지 않습니다.
 * 장점: 추가 데이터 구조 없이 간단하며, 성능이 중요하지 않은 작은 리스트에 유용합니다.
 * 단점: O(n^2) 시간 복잡도로 인해 큰 리스트에 비효율적입니다.
 */
fun findTwoSumBruteForce(nums: List<Int>, target: Int): List<Int> {
    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            Log.d("TwoSum", "Checking pair: (${nums[i]}, ${nums[j]})")
            if (nums[i] + nums[j] == target) {
                Log.d("TwoSum", "Found pair: ($i, $j)")
                return listOf(i, j)
            }
        }
    }
    Log.d("TwoSum", "No pair found")
    return emptyList()
}

@Preview(showBackground = true)
@Composable
fun PreviewTwoSumUI() {
    TwoSumUI()
}
