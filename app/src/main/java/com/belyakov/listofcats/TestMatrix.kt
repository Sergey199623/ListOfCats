package com.belyakov.listofcats

import java.lang.Exception
import java.util.InputMismatchException
import java.util.Scanner
import java.util.stream.IntStream

fun main() {

    println("Ввод параметров для 1-й матрицы")
    val condition1 = checkConditionsForMatrix()

    val matrix1 = createMatrix(
        width = condition1["width"] as Int,
        height = condition1["height"] as Int,
        isReverse = condition1["reverse"] as Boolean
    )
    printMatrix(matrix1)
    println("===========================")

    println("Ввод параметров для 2-й матрицы")
    val condition2 = checkConditionsForMatrix()

    val matrix2 = createMatrix(
        width = condition2["width"] as Int,
        height = condition2["height"] as Int,
        isReverse = condition2["reverse"] as Boolean
    )
    printMatrix(matrix2)
}

fun createMatrix(width: Int, height: Int, isReverse: Boolean) : Array<IntArray?> {
    val matrix = arrayOfNulls<IntArray>(height)
    IntStream.range(0, height).forEach { i: Int ->
        matrix[i] = IntArray(height)
    }
    var x = 0
    var y = 0
    var i = 0

    var matrixWidth = width
    val matrixSize = width * height
    while (i < matrixSize) {
        matrix[x]!![y] = if (isReverse) matrixSize - i++ else ++i
        when {
            x < matrixWidth + (width - matrixWidth) / 2 - 1 && y == (width - matrixWidth) / 2 -> x++
            x == matrixWidth + (width - matrixWidth) / 2 - 1 && y < matrixWidth + (width - matrixWidth) / 2 - 1 -> y++
            x > (width - matrixWidth) / 2 && y == matrixWidth + (width - matrixWidth) / 2 - 1 -> x--
            x == (width - matrixWidth) / 2 && y > (width - matrixWidth) / 2 + 1 -> y--
            else -> {
                x++
                matrixWidth -= 2
            }
        }
    }
    return matrix
}

private fun printMatrix(matrix: Array<IntArray?>) {
    IntStream.range(0, matrix.size).forEach { i: Int ->
        IntStream.range(0, matrix.size)
            .forEach { j: Int ->
                System.out.printf(
                    "%02d ",
                    matrix[j]!![i]
                )
            }
        println()
    }
}

fun checkConditionsForMatrix() : Map<String, Any> {
    val scanner = Scanner(System.`in`)
    val paramsMap = mutableMapOf<String, Any>()

    while (paramsMap.size < 3) {
        println("Введите высоту матрицы от 1 до 10:")
        var width: Int? = null
        while (width == null) {
            try {
                width = scanner.nextInt()
            } catch (ex: InputMismatchException) {
                println("Введено некорректное значение, повторите ввод высоты для матрицы")
                scanner.nextLine() // Очистка буфера ввода
                println("Введите ширину матрицы от 1 до 10:")
            }
            if (width !is Int || width !in 1..10) {
                println("Введено некорректное значение, повторите ввод высоты для матрицы")
                scanner.nextLine() // Очистка буфера ввода
                println("Введите высоты матрицы от 1 до 10:")
                width = null
            } else {
                paramsMap["width"] = width
                continue
            }
        }

        println("Введите ширину матрицы от 1 до 10:")
        var height: Int? = null
        while (height == null) {
            try {
                height = scanner.nextInt()
            } catch (ex: InputMismatchException) {
                println("Введено некорректное значение, повторите ввод ширины для матрицы")
                scanner.nextLine() // Очистка буфера ввода
                println("Введите ширину матрицы от 1 до 10:")
            }
            if (height !is Int || height !in 1..10) {
                println("Введено некорректное значение, повторите ввод ширины для матрицы")
                scanner.nextLine() // Очистка буфера ввода
                println("Введите ширину матрицы от 1 до 10:")
                height = null
            } else {
                paramsMap["height"] = height
                continue
            }
        }

        println("Введите TRUE или FALSE для задания реверсивного движения")
        var isReverse: Boolean? = null
        while (isReverse == null) {
            try {
                isReverse = scanner.nextBoolean()
            } catch (ex: InputMismatchException) {
                println("Введено некорректное значение для реверса")
                scanner.nextLine() // Очистка буфера ввода
                println("Введите TRUE или FALSE для задания реверсивного движения")
            }
        }
        paramsMap["reverse"] = isReverse
        break
    }
    return paramsMap
}