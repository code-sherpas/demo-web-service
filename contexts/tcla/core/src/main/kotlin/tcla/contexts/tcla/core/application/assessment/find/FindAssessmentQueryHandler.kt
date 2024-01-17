package tcla.contexts.tcla.core.application.assessment.find

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.nel
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.rule.RequesterOwnsAssessmentRule
import tcla.libraries.transactional.IsolationLevel
import tcla.libraries.transactional.TransactionExecutor
import tcla.libraries.uuidvalidation.toUuid

@Named
class FindAssessmentQueryHandler(
    private val assessmentRepository: AssessmentRepository,
    private val transactionExecutor: TransactionExecutor,
    private val requesterOwnsAssessmentRule: RequesterOwnsAssessmentRule
) {
    fun execute(query: FindAssessmentQuery): Either<NonEmptyList<Failure>, FindAssessmentSuccess> =
        transactionExecutor.transactional(IsolationLevel.READ_COMMITTED) {
            query.id.toUuid()
                .mapLeft { Failure.StringIsNotUuid.AssessmentId.nel() }
                .flatMap { AssessmentId(it).right() }
                .flatMap { assessmentId -> requesterOwnsAssessmentRule.ensure(assessmentId) }
                .flatMap { assessmentId -> assessmentRepository.find(assessmentId) }
                .flatMap { assessment -> FindAssessmentSuccess(assessment).right() }
        }
}
