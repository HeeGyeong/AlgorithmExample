package com.example.algorithmexample.util

import java.util.Locale
import kotlin.system.measureNanoTime

/**
 * 주어진 블록의 실행 시간을 측정하고 결과와 시간을 반환하는 함수.
 *
 * 이 함수는 코드 블록의 실행 시간을 나노초 단위로 측정한 후 밀리초로 변환하여 반환합니다.
 * 결과는 소숫점 세 번째 자리까지 포맷된 문자열로 제공됩니다.
 * 예를 들어, "1.234"과 같은 형식으로 반환됩니다.
 *
 * @param block 실행할 코드 블록. 이 블록은 인자가 없고 결과를 반환해야 함.
 * @return Pair<T, String> 첫 번째 요소는 블록의 실행 결과, 두 번째 요소는 실행 시간(밀리초 단위) 문자열.
 */
fun <T> measureExecutionTime(block: () -> T): Pair<T, String> {
    var result: T
    val elapsedTime = measureNanoTime {
        result = block()
    }
    val elapsedTimeInMillis = elapsedTime / 1_000_000.0 // 나노초를 밀리초로 변환
    val formattedTime = String.format(Locale.KOREA, "%.3f", elapsedTimeInMillis)
    return Pair(result, formattedTime)
}