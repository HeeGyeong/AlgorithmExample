package com.example.algorithmexample.algorithm.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.algorithmexample.ui.AlgorithmMainHeader

/**
 * 빙산 문제
 *
 * https://www.acmicpc.net/problem/2573
 */
@Composable
fun IcebergUI() {
    val result = remember { mutableStateOf<Int?>(null) }
    val initialArray = remember {
        arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 2, 4, 5, 3, 0, 0),
            intArrayOf(0, 3, 0, 2, 5, 2, 0),
            intArrayOf(0, 7, 6, 2, 4, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlgorithmMainHeader(
            title = "빙산 문제",
            algorithmName = "Graph",
            algorithmDescription = "2차원 배열에서 빙산이 분리되는 최초의 시간(년)을 구하는 문제",
        )

        Button(onClick = {
            val copiedArray = Array(5) { i ->
                IntArray(7) { j -> initialArray[i][j] }
            }
            result.value = calculateIceberg(copiedArray, 5, 7)
        }) {
            Text("계산하기")
        }

        Text("빙산 초기 상태:")
        initialArray.forEach { row ->
            Text(row.joinToString(" "))
        }

        Spacer(modifier = Modifier.height(8.dp))

        result.value?.let {
            Text("결과: $it 년")
        }
    }
}

/**
 * 빙산이 분리되는데 걸리는 시간(년)을 계산하는 함수
 * 매년 빙산의 높이가 주변 바다 칸의 개수만큼 줄어들고, 빙산이 2개 이상으로 분리되는 시점을 찾는다
 * 
 * 시간 복잡도: O(N*M*Y) - N,M은 배열 크기, Y는 빙산이 분리될 때까지 걸리는 년수
 * 공간 복잡도: O(N*M) - 임시 배열 저장용
 */
fun calculateIceberg(islandArray: Array<IntArray>, n: Int, m: Int): Int {
    var years = 0

    // 상하좌우 이동을 위한 방향 벡터
    val dy = intArrayOf(-1, 1, 0, 0)
    val dx = intArrayOf(0, 0, -1, 1)

    while (true) {
        // 반복 시작 시 빙산의 개수 확인.
        val count = checkIslandCount(islandArray, n, m)
        if (count >= 2) return years
        if (count == 0) return 0

        var nextCount = 0

        // 매 년 갱신되는 빙산의 상태를 저장하는 임시 변수
        val tmpIsland = Array(n) { IntArray(m) }

        for (i in 0 until n) {
            for (j in 0 until m) {
                if (islandArray[i][j] > 0) {
                    var waterCount = 0
                    for (k in 0..3) {
                        val moveY = i + dy[k]
                        val moveX = j + dx[k]

                        if (moveY in 0 until n && moveX in 0 until m && islandArray[moveY][moveX] == 0) {
                            waterCount++
                        }
                    }

                    tmpIsland[i][j] = maxOf(0, islandArray[i][j] - waterCount)
                    if (tmpIsland[i][j] > 0) nextCount++
                }
            }
        }

        if (nextCount == 0) return 0

        for (i in 0 until n) {
            System.arraycopy(tmpIsland[i], 0, islandArray[i], 0, m)
        }

        years++
    }
}

/**
 * DFS 함수를 사용하여 분리된 빙산의 개수를 세는 함수
 * visited 배열을 사용해 각 빙산 덩어리를 한 번씩만 카운트하도록 설정
 */
fun checkIslandCount(iceArray: Array<IntArray>, n: Int, m: Int): Int {
    val visited = Array(n) { BooleanArray(m) }
    var count = 0

    for (i in 0 until n) {
        for (j in 0 until m) {
            if (iceArray[i][j] > 0 && !visited[i][j]) {
                dfs(iceArray, visited, i, j, n, m)
                count++
            }
        }
    }

    return count
}

/**
 * 연결된 빙산을 탐색하는 DFS 함수
 * 현재 위치에서 상하좌우로 연결된 빙산을 재귀적으로 방문
 * 
 * 시간 복잡도: O(N*M) - 최악의 경우 모든 칸 방문
 * 공간 복잡도: O(N*M) - 재귀 호출 스택
 */
fun dfs(iceArray: Array<IntArray>, visited: Array<BooleanArray>, y: Int, x: Int, n: Int, m: Int) {
    // 상하좌우 이동을 위한 방향 벡터
    val dy = intArrayOf(-1, 1, 0, 0)
    val dx = intArrayOf(0, 0, -1, 1)

    // 중복 사용 방지
    visited[y][x] = true

    for (i in 0..3) {
        val moveY = y + dy[i]
        val moveX = x + dx[i]

        if (moveY in 0 until n && moveX in 0 until m && !visited[moveY][moveX] && iceArray[moveY][moveX] > 0) {
            dfs(iceArray, visited, moveY, moveX, n, m)
        }
    }
}