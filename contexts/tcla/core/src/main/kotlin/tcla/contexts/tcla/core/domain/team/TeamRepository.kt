package tcla.contexts.tcla.core.domain.team

import arrow.core.Either
import arrow.core.NonEmptyList
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.team.model.Team
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.libraries.search.Filter

interface TeamRepository {
    fun exists(id: TeamId): Either<Failure, Boolean>
    fun search(filter: Filter<TeamFilterKey>? = null): Either<NonEmptyList<Failure>, List<Team>>
    fun save(team: Team): Either<Failure, TeamId>
    fun find(teamId: TeamId) : Either<NonEmptyList<Failure>, Team>
    fun delete(id: TeamId): Either<Failure, Unit>
}
