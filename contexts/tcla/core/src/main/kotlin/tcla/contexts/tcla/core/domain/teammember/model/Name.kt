package tcla.contexts.tcla.core.domain.teammember.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right

@JvmInline
value class Name private constructor(val string: String) {

    companion object {
        operator fun invoke(value: String): Either<InvalidName, Name> =
            when(value.isNotBlank()) {
                true -> Name(value).right()
                false -> InvalidName.left()
            }
    }

    data object InvalidName
}
