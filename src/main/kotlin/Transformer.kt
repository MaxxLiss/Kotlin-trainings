class Transformer {
    inline fun <T, C : MutableCollection<T>> removeIf(
        collection: C,
        predicate: (T) -> Boolean
    ) : C {
        val toRemove = mutableListOf<T>()
        for (el in collection) if (predicate(el)) {
            toRemove.add(el)
        }
        for (el in toRemove) {
            collection.remove(el)
        }
        return collection
    }

    inline fun <T, C : MutableCollection<T>> map(
        collection: C,
        transform: (T) -> T
    ) : C {
        val tmp = collection.toList()
        collection.clear()
        tmp.forEach { collection.add( transform(it) ) }
        return collection
    }

}

fun main() {
    val a = mutableListOf(1, 2, 3, 4, 5)
    println(Transformer().map(a) { it * it })
    println(Transformer().removeIf(a) { it % 2 == 0 } )
}