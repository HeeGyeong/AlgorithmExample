package com.example.algorithmexample.algorithm.bfs

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
import java.util.LinkedList
import java.util.Queue

/**
 * 알고 스팟 문제
 *
 * https://www.acmicpc.net/problem/1261
 */
@Composable
fun AlgoSpotProblemUI() {
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
            title = "미로 탈출",
            algorithmName = "BFS",
            algorithmDescription = "미로에서 최소한의 벽을 부수고 (N, M)으로 이동하는 문제.",
        )

        TextField(
            value = inputN,
            onValueChange = { inputN = it },
            label = { Text("세로 크기 N을 입력하세요.") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = inputM,
            onValueChange = { inputM = it },
            label = { Text("가로 크기 M을 입력하세요.") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = inputArray,
            onValueChange = {
                inputArray = it
                errorMessage = ""
            },
            label = { Text("미로 상태를 입력하세요. (예: 0,1,1,1,1,1,1,1,0)") },
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
            try {
                val n = if (inputN.isEmpty()) 6 else inputN.toInt()
                val m = if (inputM.isEmpty()) 6 else inputM.toInt()
                val numbers = if (inputArray.isEmpty()) {
                    listOf(
                        0, 0, 1, 1, 1, 1,
                        0, 1, 0, 0, 0, 0,
                        0, 0, 1, 1, 1, 1,
                        1, 1, 0, 0, 0, 1,
                        0, 1, 1, 0, 1, 0,
                        1, 0, 0, 0, 1, 0
                    )
                } else {
                    inputArray.split(",").map { it.trim().toInt() }
                }
                val maze = Array(n) { i -> Array(m) { j -> numbers[i * m + j] } }

                val startTime = System.currentTimeMillis()
                output1 = minWallsToBreakBFS(maze, n, m).toString()
                val endTime = System.currentTimeMillis()
                output1 += " (BFS 수행 시간: ${endTime - startTime}ms)"
            } catch (e: Exception) {
                errorMessage = "입력값을 확인하세요."
            }
        }) {
            Text("BFS 결과 보기")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = output1)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            try {
                val n = if (inputN.isEmpty()) 6 else inputN.toInt()
                val m = if (inputM.isEmpty()) 6 else inputM.toInt()
                val numbers = if (inputArray.isEmpty()) {
                    listOf(
                        0, 0, 1, 1, 1, 1,
                        0, 1, 0, 0, 0, 0,
                        0, 0, 1, 1, 1, 1,
                        1, 1, 0, 0, 0, 1,
                        0, 1, 1, 0, 1, 0,
                        1, 0, 0, 0, 1, 0
                    )
                } else {
                    inputArray.split(",").map { it.trim().toInt() }
                }
                val maze = Array(n) { i -> Array(m) { j -> numbers[i * m + j] } }

                val startTime = System.currentTimeMillis()
                output2 = minWallsToBreakDFS(maze, n, m).toString()
                val endTime = System.currentTimeMillis()
                output2 += " (DFS 수행 시간: ${endTime - startTime}ms)"
            } catch (e: Exception) {
                errorMessage = "입력값을 확인하세요."
            }
        }) {
            Text("DFS 결과 보기")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = output2)
    }
}

/**
 * BFS를 사용하여 미로 탈출 문제를 해결합니다.
 * 큐를 사용하여 각 위치에서의 최소 벽 파괴 횟수를 추적합니다.
 * 각 방향으로 이동하며 벽을 만날 때마다 벽 파괴 횟수를 증가시킵니다.
 * 방문한 위치는 최소 벽 파괴 횟수로 업데이트하여 중복 방문을 방지합니다.
 *
 * 시작점에서 도착점까지의 최소 벽 파괴 횟수를 반환합니다.
 * BFS는 최단 경로를 보장합니다.
 *
 * 시간 복잡도: O(N * M) - 노드 방문 횟수. 모든 노드를 방문한다.
 * 공간 복잡도: O(N * M) - 큐의 최대 크기
 */
fun minWallsToBreakBFS(maze: Array<Array<Int>>, n: Int, m: Int): Int {
    val directions = arrayOf(
        intArrayOf(1, 0), // 아래
        intArrayOf(0, 1), // 오른쪽
        intArrayOf(-1, 0), // 위
        intArrayOf(0, -1) // 왼쪽
    )

    // "최소" 값을 구하는 것이기 때문에 default 값으로 최대값을 저장.
    val visited = Array(n) { IntArray(m) { Int.MAX_VALUE } }

    // 좌표 x, y와 지금까지 만난 벽의 횟수
    val queue: Queue<Triple<Int, Int, Int>> = LinkedList()

    // 시작 지점 추가
    queue.add(Triple(0, 0, 0))
    visited[0][0] = 0

    while (queue.isNotEmpty()) {
        val (x, y, walls) = queue.poll()

        for (dir in directions) {
            val nx = x + dir[0]
            val ny = y + dir[1]

            // 미로 범위 내부인 경우에만 처리
            if (nx in 0 until n && ny in 0 until m) {
                // 현재 위치까지 벽을 파괴한 횟수 + 이동할 곳 벽의 유무
                val newWalls = walls + maze[nx][ny]
                if (newWalls < visited[nx][ny]) {
                    visited[nx][ny] = newWalls
                    queue.add(Triple(nx, ny, newWalls))
                }
            }
        }
    }

    // 도착 지점 반환
    return visited[n - 1][m - 1]
}

/**
 * DFS를 사용하여 미로 탈출 문제를 해결합니다.
 * 재귀를 사용하여 각 위치에서의 최소 벽 파괴 횟수를 추적합니다.
 * 각 방향으로 이동하며 벽을 만날 때마다 벽 파괴 횟수를 증가시킵니다.
 * 방문한 위치는 최소 벽 파괴 횟수로 업데이트하여 중복 방문을 방지합니다.
 *
 * 시작점에서 도착점까지의 최소 벽 파괴 횟수를 반환합니다.
 * DFS는 모든 경로를 탐색하지만, 최단 경로를 보장하지 않습니다.
 *
 * 시간 복잡도: O(N * M) - 노드 방문 횟수. 모든 노드를 방문한다.
 * 공간 복잡도: O(N * M) - 재귀 호출 스택의 최대 깊이
 */
fun minWallsToBreakDFS(maze: Array<Array<Int>>, n: Int, m: Int): Int {
    val directions = arrayOf(
        intArrayOf(1, 0), // 아래
        intArrayOf(0, 1), // 오른쪽
        intArrayOf(-1, 0), // 위
        intArrayOf(0, -1) // 왼쪽
    )

    val visited = Array(n) { IntArray(m) { Int.MAX_VALUE } }
    visited[0][0] = 0

    // 재귀 함수 사용
    fun dfs(x: Int, y: Int, walls: Int) {
        if (x == n - 1 && y == m - 1) return

        for (dir in directions) {
            val nx = x + dir[0]
            val ny = y + dir[1]

            if (nx in 0 until n && ny in 0 until m) {
                val newWalls = walls + maze[nx][ny]
                if (newWalls < visited[nx][ny]) {
                    visited[nx][ny] = newWalls
                    dfs(nx, ny, newWalls)
                }
            }
        }
    }

    dfs(0, 0, 0)
    return visited[n - 1][m - 1]
}