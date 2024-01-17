package tcla.contexts.tcla.core.domain.teammember

import arrow.core.Either
import arrow.core.NonEmptyList
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.teammember.model.TeamMember
import tcla.contexts.tcla.core.domain.teammember.model.TeamMemberId
import tcla.libraries.search.Filter

interface TeamMemberRepository {
    fun find(id: TeamMemberId): Either<NonEmptyList<Failure>, TeamMember>
    fun exists(id: TeamMemberId): Either<CheckTeamMemberExistenceFailure, Boolean>
    fun search(filter: Filter<TeamMemberFilterKey>? = null): Either<Failure, List<TeamMember>>
    fun save(teamMember: TeamMember): Either<SaveTeamMemberFailure, TeamMemberId>
    fun delete(id: TeamMemberId): Either<NonEmptyList<Failure>, Unit>
}
