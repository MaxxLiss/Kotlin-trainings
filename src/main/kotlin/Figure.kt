import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.PI

class UnknownFigureTypeException : Exception()

class IllegalFigurePropertyException : Exception()

interface Figure {
    fun computeArea() : Double

    fun computePerimeter() : Double

    fun getName() : String
}

class Circle(
    private val radius: Double
    ) : Figure {
    override fun computeArea(): Double {
        return PI * radius * radius
    }

    override fun computePerimeter(): Double {
        return 2 * PI * radius
    }

    override fun getName(): String {
        return "Circle"
    }

    override fun toString(): String {
        return "Circle with radius $radius"
    }
}

class Square(
    private val length: Double
    ) : Figure {
    override fun computeArea(): Double {
        return length * length
    }

    override fun computePerimeter(): Double {
        return length * 4
    }

    override fun getName(): String {
        return "Square"
    }

    override fun toString(): String {
        return "Square with length $length"
    }
}

object FigureCollection : ArrayList<Figure>()

class FigureManager(
    private val figureCollection : FigureCollection
) {
    private var isWork = true
    init {
        work()
    }

    private fun work() {
        while (isWork) {
            val input : String = readLine() ?: continue
            val data = input.split(" ")

            when(data[0]) {
                "1" -> printArea()
                "2" -> printPerimeter()
                "3" -> addFigure(data[1], data[2])
                "4" -> isWork = false
            }
        }
    }

    private fun printArea() {
        for ((i, item) in figureCollection.withIndex()) {
            println("${i + 1} ${item.getName()} ${item.computeArea()}")
        }
    }

    private fun printPerimeter() {
        for ((i, item) in figureCollection.withIndex()) {
            println("${i + 1} ${item.getName()} ${item.computePerimeter()}")
        }
    }

    private fun addFigure(figureType: String, figureData: String) {
        val figureDouble = figureData.toDoubleOrNull()
        if (figureDouble == null || figureDouble <= 0) {
            throw IllegalFigurePropertyException()
        }
        when(figureType) {
            "Circle" -> {
                figureCollection.add(Circle(figureDouble))
            }
            "Square" -> {
                figureCollection.add(Square(figureDouble))
            }
            else -> {
                throw UnknownFigureTypeException()
            }
        }
    }
}

fun main() {
    val figureManager = FigureManager(FigureCollection)
}