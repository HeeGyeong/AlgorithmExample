# AlgorithmExample

알고리즘 문제 해결을 위한 샘플 프로젝트입니다.

각 알고리즘의 구현과 설명을 Jetpack Compose를 사용하여 UI로 표현했습니다.

## 프로젝트 설명

- Jetpack Compose를 사용한 UI 구현
- 다양한 알고리즘 문제 해결 방식 제공
- 각 알고리즘의 장단점 설명
- 시간/공간 복잡도가 중요한 알고리즘인 경우 시간/공간 복잡도 표기
- Preview를 통한 실시간 결과 확인 가능
- Input되는 데이터가 많은 경우 Default 데이터를 사용하여 확인 가능

## 구현된 알고리즘

### Hash
- Two Sum
  - Hash Map을 사용한 구현
  - Brute Force 방식 구현

### Two Pointer
- Palindrome
  - Two Pointer 방식 구현
  - Stack을 사용한 구현
  - String Reverse 방식 구현

### Backtracking
- N and M
  - 주어진 숫자와 수열에서 중복되지 않으면서 오름차순인 수열을 구하는 문제

### Graph
- Tomato
  - 주어진 2차원 배열의 창고에서 토마토가 전부 익는데 걸리는 시간을 구하는 문제
- Iceberg
  - 2차원 배열에서 빙산이 분리되는 최초의 시간(년)을 구하는 문제

### Dynamic Programming
- Wine Tasting
  - 연속으로 3잔을 마실 수 없을 때, 최대로 마실 수 있는 포도주의 양을 구하는 문제
- Palindrome
  - 최대 짝수 팰린드롬 분할 개수를 구하는 문제
  - Dynamic Programming과 Two Pointer 방식을 사용해 구현.

## 기술 스택

- Kotlin
- Jetpack Compose
- Android SDK 34
- Gradle 8.7

## 실행 방법

1. 프로젝트를 클론합니다
2. Android Studio에서 프로젝트를 엽니다
3. 원하는 알고리즘의 Preview를 실행합니다
