package com.example.algorithmexample.algorithm.pointer

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
import androidx.compose.ui.unit.dp
import com.example.algorithmexample.ui.AlgorithmMainHeader

@Composable
fun PalindromeLinkedListUI() {
    var input by remember { mutableStateOf("") }
    val results = remember { mutableStateOf(listOf<Boolean?>(null, null, null)) }
    val defaultInputText =
        "1,2,3,4,5,6,7,8,9,0,9,8,7,6,5,4,3,2,1,0,1,2,3,4,5,6,7,8,9,0,9,8,7,6,5,4,3,2,1"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "Palindrome Linked List",
            algorithmName = "Palindrome",
            algorithmDescription = "주어진 문자열이 팰린드롬(palindrome)인지 확인하는 문제",
        )

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("팰린드롬을 확인할 문자열을 입력해주세요.") }
        )

        val algorithms = listOf(
            "to pointer Run" to ::isPalindromeList,
            "stack Run" to ::isPalindromeUsingStack,
            "reverse string Run" to ::isPalindromeUsingReverseString
        )

        algorithms.forEachIndexed { index, (buttonText, algorithm) ->
            Button(onClick = {
                results.value = results.value.toMutableList().apply {
                    val inputList = input.ifEmpty { defaultInputText }.split(",").map { it.trim() }
                    if (inputList.any { it.isEmpty() }) {
                        Log.e("PalindromeLinkedList", "Invalid input: $input")
                    } else {
                        this[index] = algorithm(inputList)
                    }
                }
            }) {
                Text(buttonText)
            }
            Text(text = "Output: ${results.value[index]}")
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/**
 * 두 포인터를 사용하여 리스트가 팰린드롬인지 확인합니다.
 * 리스트의 양 끝에서 시작하여 중앙으로 이동하며, 각 요소를 비교합니다.
 * 만약 양 끝의 요소가 다르면 팰린드롬이 아닙니다.
 *
 * 시간 복잡도: O(n) - 리스트의 모든 요소를 한 번씩 확인합니다.
 * 공간 복잡도: O(1) - 추가적인 공간을 거의 사용하지 않습니다.
 * 장점: 공간 효율적이며, 구현이 간단합니다.
 * 단점: 리스트의 요소를 직접 비교하므로, 요소의 타입에 따라 성능이 달라질 수 있습니다.
 */
fun isPalindromeList(nums: List<String>): Boolean {
    val startTime = System.currentTimeMillis()
    if (nums.isEmpty()) return true
    var left = 0
    var right = nums.size - 1
    while (left < right) {
        if (nums[left] != nums[right]) {
            Log.d(
                "Palindrome",
                "isPalindromeList time: ${System.currentTimeMillis() - startTime} ms"
            )
            return false
        }
        left++
        right--
    }
    Log.d("Palindrome", "isPalindromeList time: ${System.currentTimeMillis() - startTime} ms")
    return true
}

/**
 * 스택을 사용하여 리스트가 팰린드롬인지 확인합니다.
 * 리스트의 절반을 스택에 저장한 후, 나머지 절반과 비교합니다.
 * 스택의 요소와 리스트의 나머지 절반이 일치하지 않으면 팰린드롬이 아닙니다.
 *
 * 시간 복잡도: O(n) - 리스트의 절반을 스택에 저장하고 나머지 절반을 비교합니다.
 * 공간 복잡도: O(n) - 리스트의 절반 크기만큼의 스택을 사용합니다.
 * 장점: 구현이 직관적이며, 리스트의 절반만 비교하므로 효율적입니다.
 * 단점: 스택을 사용하므로 추가적인 공간이 필요합니다.
 */
fun isPalindromeUsingStack(nums: List<String>): Boolean {
    val startTime = System.currentTimeMillis()
    if (nums.isEmpty()) return true
    val stack = mutableListOf<String>()
    val mid = nums.size / 2

    for (i in 0 until mid) {
        stack.add(nums[i])
    }

    for (i in (if (nums.size % 2 == 0) mid else mid + 1) until nums.size) {
        if (stack.isEmpty() || stack.removeAt(stack.size - 1) != nums[i]) {
            Log.d(
                "Palindrome",
                "isPalindromeUsingStack time: ${System.currentTimeMillis() - startTime} ms"
            )
            return false
        }
    }
    Log.d("Palindrome", "isPalindromeUsingStack time: ${System.currentTimeMillis() - startTime} ms")
    return true
}

/**
 * 문자열을 뒤집는 방법으로 리스트가 팰린드롬인지 확인합니다.
 * 리스트를 문자열로 변환한 후, 그 문자열을 뒤집어 원래 문자열과 비교합니다.
 * 두 문자열이 같으면 팰린드롬입니다.
 *
 * 시간 복잡도: O(n) - 문자열을 뒤집고 비교하는 데 걸리는 시간입니다.
 * 공간 복잡도: O(n) - 뒤집은 문자열을 저장하기 위한 공간이 필요합니다.
 * 장점: 구현이 간단하며, 문자열 비교로 결과를 쉽게 확인할 수 있습니다.
 * 단점: 문자열 변환과 뒤집기에 추가적인 공간이 필요합니다.
 */
fun isPalindromeUsingReverseString(nums: List<String>): Boolean {
    val startTime = System.currentTimeMillis()
    if (nums.isEmpty()) return true
    val original = nums.joinToString("")
    val reversed = original.reversed()
    val result = original == reversed
    Log.d(
        "Palindrome",
        "isPalindromeUsingReverseString time: ${System.currentTimeMillis() - startTime} ms"
    )
    return result
}
