import java.io.File
import java.lang.reflect.Field

class A(
    val a: Int,
    val b: Int
    )

class User(
    private val name: String,
    val age: Int,
    val a: A,
    private val list: List<Any>,
)

object Json {
    private var sep: Int = 0
    private lateinit var file: File

    fun writeToFile(toBeWritten: Any) {
        val fileName = toBeWritten::class.simpleName
        file = File("$fileName.js")

        file.writeText("")

        writeObject(toBeWritten)
    }

    private fun writeObject(obj: Any?) {
        when (obj) {
            null -> file.appendText("null")
            is Boolean -> file.appendText(if (obj) "true" else "false")
            is Number -> file.appendText("$obj")
            is String -> writeString(obj)
            is Collection<*> -> {
                file.appendText("[\n")

                ++sep
                for ((index, item) in obj.withIndex()) {

                    useSep()
                    writeObject(item)
                    if (index != obj.size - 1) {
                        file.appendText(",")
                    }
                    file.appendText("\n")

                }
                --sep

                useSep()
                file.appendText("]")
            }
            else -> writeClass(obj)
        }
    }

    private fun writeClass(cls: Any) {
        file.appendText("{\n")

        val fields = cls::class.java.declaredFields

        ++sep
        for ((index, field) in fields.withIndex()) {
            field.isAccessible = true

            useSep()
            writeString(field.name)
            file.appendText(" : ")

            writeObject(field.get(cls))

            if (index != fields.size - 1) {
                file.appendText(",")
            }
            file.appendText("\n")
        }
        --sep

        useSep()
        file.appendText("}")
    }

    private fun writeString(string: String) {
        file.appendText("\"$string\"")
    }

    private fun useSep() {
        repeat(sep) {
            file.appendText(" ")
        }
    }
}

fun foo(a: Any?) {
    when (a) {
        is String -> println("String")
        is Number -> println("Number")
        is Int -> println("Int")
        null -> println("null")
    }
}

fun main() {
    val a = A(1, 2)
    val user = User("V", 19, a, listOf(1, "aba", 1.0, true, A(2, 3)))
//    foo("a")
//    foo(1.0)
//    foo(1)
//    foo(null)

    Json.writeToFile(user)
}