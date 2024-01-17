package tcla.contexts.tcla.database.springdatajpa.teammember

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.nel
import arrow.core.right
import jakarta.inject.Named
import org.springframework.data.repository.findByIdOrNull
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.contexts.tcla.core.domain.teammember.CheckTeamMemberExistenceFailure
import tcla.contexts.tcla.core.domain.teammember.SaveTeamMemberFailure
import tcla.contexts.tcla.core.domain.teammember.TeamMemberFilterKey
import tcla.contexts.tcla.core.domain.teammember.TeamMemberRepository
import tcla.contexts.tcla.core.domain.teammember.model.TeamMember
import tcla.contexts.tcla.core.domain.teammember.model.TeamMemberId
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeam
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeamRepository
import tcla.libraries.search.Filter
import tcla.libraries.search.ManyValuesFilter
import tcla.libraries.search.OneValueFilter
import tcla.libraries.search.Operator

@Named
class SqlTeamMemberRepository(
    private val jpaTeamMemberRepository: JpaTeamMemberRepository,
    private val jpaTeamRepository: JpaTeamRepository
) : TeamMemberRepository {
    override fun find(id: TeamMemberId): Either<NonEmptyList<Failure>, TeamMember> =
        Either.catch { jpaTeamMemberRepository.findByIdOrNull(id.uuid) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaTeamMember: JpaTeamMember? ->
                when(jpaTeamMember) {
                    null -> Failure.EntityNotFound.TeamMember.nel().left()
                    else -> jpaTeamMember.toDomain().right()
                }
            }

    override fun exists(id: TeamMemberId): Either<CheckTeamMemberExistenceFailure, Boolean> =
        Either.catch { jpaTeamMemberRepository.existsById(id.uuid) }
            .mapLeft { CheckTeamMemberExistenceFailure.DatabaseException(it) }
            .flatMap { exists -> exists.right() }


    override fun search(filter: Filter<TeamMemberFilterKey>?): Either<Failure, List<TeamMember>> =
        findJpaTeamMembers(filter)
            .flatMap { jpaTeamMembers: List<JpaTeamMember> ->
                Either.catch { jpaTeamMembers.map { jpaTeamMember -> jpaTeamMember.toDomain() } }
                    .mapLeft { Failure.UnableToTransformIntoDomainData.TeamMember }
            }

    override fun save(teamMember: TeamMember): Either<SaveTeamMemberFailure, TeamMemberId> =
        Either.catch { jpaTeamRepository.findByIdOrNull(teamMember.teamId.uuid) }
            .mapLeft { TODO("Code not present in this demo") }
            .flatMap { jpaTeam ->
                when (jpaTeam) {
                    null ->  TODO("Code not present in this demo")
                    else -> jpaTeam.right()
                }
            }.flatMap { jpaTeam: JpaTeam ->
                teamMember.toJpa(jpaTeam)
                    .let { jpaTeamMember ->
                        Either.catch { jpaTeamMemberRepository.save(jpaTeamMember) }.mapLeft { TODO("Code not present in this demo") }
                    }.flatMap { TeamMemberId(it.id).right() }
            }

    override fun delete(id: TeamMemberId): Either<NonEmptyList<Failure>, Unit> =
        Either.catch { jpaTeamMemberRepository.deleteById(id.uuid) }
            .mapLeft { Failure.DatabaseException(it).nel() }

    private fun findJpaTeamMembers(filter: Filter<TeamMemberFilterKey>?): Either<Failure, List<JpaTeamMember>> =
        when (filter) {
            null -> Either.catch { jpaTeamMemberRepository.findAll() }
                .mapLeft(Failure::DatabaseException)

            else -> when (filter) {
                is ManyValuesFilter<TeamMemberFilterKey, *> -> Failure.UnsupportedDatabaseFilter.TeamMember.left()

                is OneValueFilter<TeamMemberFilterKey, *> -> when (filter.key) {
                    TeamMemberFilterKey.TEAM -> when (val value = filter.value) {
                        is TeamId -> when (filter.operator) {
                            Operator.BinaryOperator.Equal -> Either.catch {
                                jpaTeamMemberRepository.findAllByTeam_Id(value.uuid)
                            }.mapLeft { throwable -> Failure.DatabaseException(throwable) }

                            Operator.NaryOperator.In -> Failure.UnsupportedDatabaseFilter.TeamMember.left()
                        }

                        else -> Failure.UnsupportedDatabaseFilter.TeamMember.left()
                    }
                }
            }
        }
}
