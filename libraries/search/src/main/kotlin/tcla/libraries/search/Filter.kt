package tcla.libraries.search

sealed class Filter<K : Any> {
    abstract val key: K
    abstract val operator: Operator
}
