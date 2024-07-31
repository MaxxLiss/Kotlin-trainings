class Victor private constructor(
    private var size: Int,
    private var capacity: Int,
) {
    private var data = Array<Int>(capacity) { 0 }

    constructor(size: Int) : this(size, if (size > 0) size * 2 else 2) {}

    private fun expand() {
        val newData = Array<Int>(2 * capacity) { 0 }
        for (i in 0 until capacity) {
            newData[i] = data[i]
        }
        data = newData
        capacity *= 2
    }

    fun add(element: Int) {
        if (size == capacity) {
            expand()
        }
        data[size] = element
        ++size
    }

    fun get(index: Int) : Int {
        return data[index]
    }

    fun set(index: Int, value: Int) {
        data[index] = value
    }

    fun size() : Int {
        return size
    }

    fun pop() : Boolean {
        if (size == 0) return false
        --size
        return true
    } //return true если удалось удалить, если не удалить return false

    fun printVictor() {
        for (i in 0 until size) print("${data[i]} ")
        println()
    }
}

fun main() {
    val victor = Victor(0)

    println("Test empty pop:")
    if (victor.pop()) {
        println("Pop works incorrect")
        return
    } else {
        println("Pop works correct")
    }

    println()

    println("Test add and size:")
    for (i in 0..9) {
        victor.add(i)
        print("${i + 1}. ")
        if (victor.size() != i + 1) {
            println("Wrong size")
            return
        } else {
            victor.printVictor()
        }
    }

    println()

    println("Test add and get:")
    for (i in 0..9) {
        if (victor.get(i) != i) {
            println("${i}th element's incorrect")
            return
        } else {
            print("${victor.get(i)} ")
        }
    }

    println()
    println()

    println("Test non-empty pop:")

    repeat(10) {
        if (!victor.pop()) {
            println("Wrong pop result")
            return
        } else if (victor.size() != 9 - it) {
            println("Wrong size")
            return
        } else {
            print("${it + 1}. ")
            victor.printVictor()
        }
    }

}