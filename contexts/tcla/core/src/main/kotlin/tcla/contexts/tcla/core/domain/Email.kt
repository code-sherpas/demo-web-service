package tcla.contexts.tcla.core.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right

@JvmInline
value class Email private constructor(val string: String) {

    companion object {
        operator fun invoke(value: String): Either<InvalidEmail, Email> =
            TODO("Code not present in this demo")
    }

    data object InvalidEmail
}
