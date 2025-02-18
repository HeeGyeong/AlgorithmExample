package com.example.algorithmexample.algorithm.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import java.util.LinkedList
import java.util.Queue

/**
 * Tomato 문제
 *
 * https://www.acmicpc.net/problem/7576
 */
@Composable
fun TomatoUI() {
    var n by remember { mutableStateOf("") }
    var m by remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }
    val isClick = remember { mutableStateOf(false) }

    // 토마토 박스 예제들을 리스트로 정의
    val tomatoExamples = remember {
        listOf(
            Triple(
                6, 4, arrayOf(
                    intArrayOf(0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 1)
                )
            ),
            Triple(
                6, 4, arrayOf(
                    intArrayOf(0, -1, 0, 0, 0, 0),
                    intArrayOf(-1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 1)
                )
            ),
            Triple(
                6, 4, arrayOf(
                    intArrayOf(1, -1, 0, 0, 0, 0),
                    intArrayOf(0, -1, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, -1, 0),
                    intArrayOf(0, 0, 0, 0, -1, 1)
                )
            ),
            Triple(
                5, 5, arrayOf(
                    intArrayOf(-1, 1, 0, 0, 0),
                    intArrayOf(0, -1, -1, -1, 0),
                    intArrayOf(0, -1, -1, -1, 0),
                    intArrayOf(0, -1, -1, -1, 0),
                    intArrayOf(0, 0, 0, 0, 0)
                )
            ),
            Triple(
                2, 2, arrayOf(
                    intArrayOf(1, -1),
                    intArrayOf(-1, 1)
                )
            )
        )
    }

    var currentTomatoBox by remember { mutableStateOf(tomatoExamples[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "Tomato Problem",
            algorithmName = "Graph",
            algorithmDescription = "주어 2차원 배열의 창고에서 토마토가 전부 익는데 까지 걸리는 시간을 구하는 문제.",
        )

        Button(onClick = {
            currentTomatoBox = tomatoExamples.random()
            n = currentTomatoBox.second.toString()
            m = currentTomatoBox.first.toString()

            val (funResult, elapsedTime) = measureExecutionTime {
                calculateTomato(
                    currentTomatoBox.third,
                    currentTomatoBox.second,
                    currentTomatoBox.first
                )
            }
            result.value = "결과: $funResult (수행 시간 ${elapsedTime}ms)"
            isClick.value = true
        }) {
            Text("예제 랜덤으로 계산하기")
        }

        Text("토마토 박스 초기 상태")

        if (isClick.value) {
            currentTomatoBox.third.forEach { row ->
                Text(row.joinToString(" "))
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (result.value.isNotEmpty()) {
            Text(result.value)
        }

    }
}

/**
 * BFS를 사용하여 토마토가 모두 익는데 걸리는 최소 일수를 계산힌디.
 * 초기에 반복을 돌며 익은 토마토의 위치를 큐에 저장하고, 익지 않은 토마토의 개수를 세어 둔다.
 * for 문을 사용하여 큐에서 익은 토마토를 하나씩 꺼내며 상하좌우로 인접한 토마토들을 익게 만든다.
 * 상위에 있는 while문은 1일에 한번씩 반복되게 한다.
 *
 * 시간 복잡도: O(N*M) - 모든 토마토를 한 번씩 방문.
 * 공간 복잡도: O(N*M) - 최악의 경우 모든 토마토가 큐에 들어갈 수 있음.
 * 장점: BFS를 통해 최소 일수를 보장할 수 있습니다.
 * 단점: 토마토 박스 전체를 메모리에 저장해야 합니다.
 */
fun calculateTomato(tomatoBox: Array<IntArray>, n: Int, m: Int): Int {
    var days = 0
    var normalTomato = 0
    val queue: Queue<Pair<Int, Int>> = LinkedList()

    // 초기 상태 확인
    for (i in 0 until n) {
        for (j in 0 until m) {
            when (tomatoBox[i][j]) {
                0 -> normalTomato++
                1 -> queue.add(Pair(i, j))
            }
        }
    }

    if (normalTomato == 0) return 0

    // 상하좌우 이동을 위한 방향 벡터
    val movePoint = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    while (queue.isNotEmpty()) {
        val size = queue.size  // 현재 날짜에 처리할 토마토의 수

        // 한 날짜에 처리할 수 있는 모든 토마토 방문
        for (index in 0 until size) {
            val (y, x) = queue.poll()!!

            for ((moveY, moveX) in movePoint) {
                val resultY = y + moveY
                val resultX = x + moveX

                // 범위 내에 있고 익지 않은 토마토인 경우
                if (resultX in 0 until m && resultY in 0 until n && tomatoBox[resultY][resultX] == 0) {
                    tomatoBox[resultY][resultX] = 1
                    queue.add(Pair(resultY, resultX))
                    normalTomato--
                }
            }
        }

        // 다음 날짜로 진행할 토마토가 있는 경우 날짜 증가
        if (queue.isNotEmpty()) days++
    }

    return if (normalTomato == 0) days else -1
}
