package tcla.libraries.search

data class ManyValuesFilter<K : Any, V : Any>(
    override val key: K,
    val values: List<V>,
    override val operator: Operator = Operator.NaryOperator.In
) : Filter<K>()
