package tcla.contexts.tcla.core.domain.assessment.rule

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.nel
import arrow.core.nonEmptyListOf
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.authentication.core.RequestInfo
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.team.TeamRepository

@Named
class RequesterOwnsAssessmentRule(
    private val assessmentRepository: AssessmentRepository,
    private val teamRepository: TeamRepository
) {
    fun ensure(
        assessment: Assessment,
        requesterId: String
    ): Either<NonEmptyList<Failure>, AssessmentId> =
        teamRepository.find(assessment.teamId)
            .flatMap { team ->
                when (team.ownerId.string) {
                    requesterId -> assessment.id.right()
                    else -> nonEmptyListOf(Failure.RequesterDoesNotOwnResource.Assessment).left()
                }
            }

    fun ensure(
        assessment: Assessment
    ): Either<NonEmptyList<Failure>, AssessmentId> =
        RequestInfo.getRequesterId()
            .let { requesterId: String? ->
                when (requesterId) {
                    null -> Failure.RequestNotAuthenticated.nel().left()
                    else -> ensure(assessment, requesterId)
                }
            }

    fun ensure(
        assessmentId: AssessmentId,
        requesterId: String
    ): Either<NonEmptyList<Failure>, AssessmentId> =
        assessmentRepository.find(assessmentId)
            .flatMap { assessment -> ensure(assessment, requesterId) }

    fun ensure(
        assessmentId: AssessmentId
    ): Either<NonEmptyList<Failure>, AssessmentId> =
        assessmentRepository.find(assessmentId)
            .flatMap { assessment -> ensure(assessment) }

}
