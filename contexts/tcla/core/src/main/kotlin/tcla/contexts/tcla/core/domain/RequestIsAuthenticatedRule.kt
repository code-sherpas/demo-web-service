package tcla.contexts.tcla.core.domain

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.nel
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.authentication.core.RequestInfo
import tcla.contexts.tcla.core.application.failures.Failure

@Named
class RequestIsAuthenticatedRule {
    fun ensure(): Either<NonEmptyList<Failure>, String> =
        TODO("Code not present in this demo")
}
