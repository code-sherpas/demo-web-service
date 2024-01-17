package tcla.contexts.tcla.core.domain.assessment.rule

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.right
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tcla.contexts.tcla.core.application.failures.CreateAssessmentFailure
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.team.model.Team
import tcla.contexts.tcla.core.domain.team.model.TeamMother
import tcla.contexts.tcla.core.domain.teammember.TeamMemberFilterKey
import tcla.contexts.tcla.core.domain.teammember.TeamMemberRepository
import tcla.contexts.tcla.core.domain.teammember.model.TeamMemberMother
import tcla.libraries.search.OneValueFilter

class TeamSizeIsBigEnoughRuleTest {
    @Test
    fun `it fails when team size IS NOT big enough`() {
        val team = TeamMother.default()
        val teamMemberRepository = mockk<TeamMemberRepository>()
        val teamSizeIsBigEnoughRule = TeamSizeIsBigEnoughRule(teamMemberRepository)
        val teamMember1 = TeamMemberMother.default(teamId = team.id)
        val teamMember2 = TeamMemberMother.default(teamId = team.id)

        every { teamMemberRepository.search(OneValueFilter(TeamMemberFilterKey.TEAM, team.id)) }
            .returns(listOf(teamMember1, teamMember2).right())

        val result: Either<NonEmptyList<Failure>, Team> = teamSizeIsBigEnoughRule.ensure(team)

        result.fold(
            { failures -> assertThat(failures).containsExactlyInAnyOrderElementsOf(listOf(CreateAssessmentFailure.TeamIsNotBigEnough)) },
            { Assertions.fail("It must be left") }
        )
    }

    @Test
    fun `it passes when team size IS big enough`() {
        val team = TeamMother.default()
        val teamMemberRepository = mockk<TeamMemberRepository>()
        val teamSizeIsBigEnoughRule = TeamSizeIsBigEnoughRule(teamMemberRepository)
        val teamMember1 = TeamMemberMother.default(teamId = team.id)
        val teamMember2 = TeamMemberMother.default(teamId = team.id)
        val teamMember3 = TeamMemberMother.default(teamId = team.id)

        every { teamMemberRepository.search(OneValueFilter(TeamMemberFilterKey.TEAM, team.id)) }
            .returns(listOf(teamMember1, teamMember2, teamMember3).right())

        val result: Either<NonEmptyList<Failure>, Team> = teamSizeIsBigEnoughRule.ensure(team)

        result.fold(
            { _ -> Assertions.fail<String>("It must be right") },
            { assertThat(it).usingRecursiveComparison().isEqualTo(team) }
        )
    }
}
