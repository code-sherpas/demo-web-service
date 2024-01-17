package tcla.contexts.tcla.core.domain.assessment.rule

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.nel
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.CreateAssessmentFailure
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.team.model.Team
import tcla.contexts.tcla.core.domain.teammember.TeamMemberFilterKey
import tcla.contexts.tcla.core.domain.teammember.TeamMemberRepository
import tcla.libraries.search.OneValueFilter

@Named
class TeamSizeIsBigEnoughRule(
    private val teamMemberRepository: TeamMemberRepository
) {
    fun ensure(team: Team): Either<NonEmptyList<Failure>, Team> =
        teamMemberRepository.search(OneValueFilter(TeamMemberFilterKey.TEAM, team.id))
            .mapLeft { failure -> failure.nel() }
            .flatMap { teamMembers ->
                when (teamMembers.size) {
                    in 0..2 -> CreateAssessmentFailure.TeamIsNotBigEnough.nel().left()
                    else -> team.right()
                }
            }
}
