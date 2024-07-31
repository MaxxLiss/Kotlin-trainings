object Transformer {
    inline fun <T, C : MutableCollection<T>> removeIf(
        collection: C,
        predicate: (T) -> Boolean
    ) : C {
        val toRemove = mutableListOf<T>()
        collection.forEach {
            if (predicate(it)) {
                toRemove.add(it)
            }
        }
        toRemove.forEach { collection.remove(it) }
        return collection
    }

    inline fun <T, C : MutableCollection<T>, R> map(
        collection: C,
        transform: (T) -> R
    ) : List<R> {
        val result = mutableListOf<R>()
        collection.forEach { result.add( transform(it) ) }
        return result
    }

}

fun main() {
    val a = mutableListOf(1, 2, 3, 4, 5)
    println(Transformer.map(a) { it * it })
    println(Transformer.map(a) { it.toString() })
    println(Transformer.removeIf(a) { it % 2 == 0 } )

    val b = mutableSetOf(1.0, 2.0, 3.0, 4.0, 5.0)
    println(Transformer.map(b) { it * it })
    println(Transformer.map(b) { it.toString() })
    println(Transformer.removeIf(b) { it - 3 > 0 } )
}