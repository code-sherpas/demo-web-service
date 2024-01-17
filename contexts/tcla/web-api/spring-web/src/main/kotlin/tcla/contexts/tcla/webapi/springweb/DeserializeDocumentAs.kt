package tcla.contexts.tcla.webapi.springweb

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nel
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.libraries.jsonserialization.deserialization.deserializeJsonAs
import kotlin.reflect.KClass


fun <T : Any> String.deserializeDocumentAs(type: KClass<T>): Either<NonEmptyList<Failure>, T> =
    deserializeJsonAs(type).mapLeft { Failure.InvalidDocument.nel() }
