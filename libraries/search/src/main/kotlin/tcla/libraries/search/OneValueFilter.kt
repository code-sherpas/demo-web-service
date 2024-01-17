package tcla.libraries.search

data class OneValueFilter<K : Any, V : Any>(
    override val key: K,
    val value: V,
    override val operator: Operator = Operator.BinaryOperator.Equal
) : Filter<K>()
