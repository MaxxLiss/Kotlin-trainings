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
    fun writeToFile(toBeWritten: Any) {
        val fileName = toBeWritten::class.simpleName
        val file = File("$fileName.json")

        file.writeText(writeObject(toBeWritten, 0))
    }

    private fun writeObject(obj: Any?, sep: Int): String {
        var result = ""

        when (obj) {
            null -> result = result.plus("null")
            is Boolean -> result = result.plus(if (obj) "true" else "false")
            is Number -> result = result.plus("$obj")
            is String -> result = result.plus(writeString(obj))
            is Collection<*> -> {
                result = result.plus("[\n")

                for ((index, item) in obj.withIndex()) {
                    result = result.plus(useSep(sep + 1))

                    result = result.plus(writeObject(item, sep + 1))

                    if (index != obj.size - 1) {
                        result = result.plus(",")
                    }
                    result = result.plus("\n")
                }

                result = result.plus(useSep(sep))
                result = result.plus("]")
            }
            else -> result = result.plus(writeClass(obj, sep))
        }

        return result
    }

    private fun writeClass(cls: Any, sep: Int): String {
        var result = "{\n"
        val fields = cls::class.java.declaredFields

        for ((index, field) in fields.withIndex()) {
            field.isAccessible = true

            result = result.plus(useSep(sep + 1))

            result = result.plus(writeString(field.name))
            result = result.plus(" : ")

            result = result.plus(writeObject(field.get(cls), sep + 1))

            if (index != fields.size - 1) {
                result = result.plus(",")
            }
            result = result.plus("\n")
        }

        result = result.plus(useSep(sep))
        result = result.plus("}")

        return result
    }

    private fun writeString(string: String): String {
        return "\"$string\""
    }

    private fun useSep(sep: Int): String {
        return " ".repeat(sep)
    }
}

fun main() {
    val user = User("V", 19, A(1, 2), listOf(1, "aba", 1.0, true, A(2, 3)))

    Json.writeToFile(user)
}