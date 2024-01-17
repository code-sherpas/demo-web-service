package tcla.contexts.tcla.core.domain.team.model

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import tcla.contexts.tcla.core.application.failures.TeamFailure
import tcla.contexts.tcla.core.domain.teamowner.model.TeamOwnerId
import tcla.contexts.tcla.core.domain.timezone.isValidTimeZone
import java.util.TimeZone

data class Team private constructor(
    val id: TeamId,
    val name: Name,
    val timeZone: TimeZone,
    val ownerId: TeamOwnerId
) {
    companion object {
        operator fun invoke(
            id: TeamId,
            name: String,
            timeZone: String,
            ownerId: TeamOwnerId
        ): Either<NonEmptyList<TeamFailure>, Team> = TODO("Code not present in this demo")
    }
}
