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
