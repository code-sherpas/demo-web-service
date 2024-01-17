package tcla.libraries.jsonserialization.deserialization

sealed class DeserializeJsonFailure {
    data object JsonStructureNotMatchObject : DeserializeJsonFailure()
    data object BuildAdapter : DeserializeJsonFailure()
}
