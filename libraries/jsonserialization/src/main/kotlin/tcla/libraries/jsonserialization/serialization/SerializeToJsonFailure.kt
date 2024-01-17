package tcla.libraries.jsonserialization.serialization

sealed class SerializeToJsonFailure {
    data object ToJson : SerializeToJsonFailure()
    data object BuildAdapter : SerializeToJsonFailure()
}
