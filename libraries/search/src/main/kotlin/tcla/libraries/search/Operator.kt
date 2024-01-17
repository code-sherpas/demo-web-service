package tcla.libraries.search

sealed class Operator {
    sealed class BinaryOperator: Operator() {
        data object Equal : BinaryOperator()
    }
    sealed class NaryOperator: Operator() {
        data object In : NaryOperator()
    }
}
