package tcla.contexts.tcla.database.springdatajpa

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.nel
import arrow.core.right
import tcla.contexts.tcla.core.application.failures.TimeZoneToDomainFailure
import tcla.contexts.tcla.core.domain.timezone.isValidTimeZone
import java.util.TimeZone


fun String.toDomainTimeZone(): Either<NonEmptyList<TimeZoneToDomainFailure>, TimeZone> =
    when (isValidTimeZone(this)) {
        true -> TimeZone.getTimeZone(this).right()
        false -> TimeZoneToDomainFailure.UnknownTimeZone.nel().left()
    }

