package tcla.contexts.tcla.core.domain.assessment.cancel

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.nonEmptyListOf
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.CancelAssessmentFailure
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.application.failures.StepFailure
import tcla.contexts.tcla.core.domain.RequestIsAuthenticatedRule
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.model.Step
import tcla.contexts.tcla.core.domain.assessment.rule.RequesterOwnsAssessmentRule
import tcla.contexts.tcla.core.domain.questionnaire.model.QuestionnaireId
import tcla.libraries.transactional.IsolationLevel
import tcla.libraries.transactional.TransactionExecutor

@Named
class CancelAssessment(
    private val transactionExecutor: TransactionExecutor,
    private val requestIsAuthenticatedRule: RequestIsAuthenticatedRule,
    private val requesterOwnsAssessmentRule: RequesterOwnsAssessmentRule,
) {
    fun execute(assessment: Assessment): Either<NonEmptyList<Failure>, Assessment> =
        transactionExecutor.transactional(isolationLevel = IsolationLevel.REPEATABLE_READ) {
            ensureRequestIsAuthenticated()
                .flatMap { requesterId -> ensureRequesterOwnsAssessment(assessment, requesterId) }
                .flatMap { ensureSurveyExists(assessment) }
                .flatMap { cancelAssessment(assessment) }
        }

    private fun ensureSurveyExists(assessment: Assessment): Either<NonEmptyList<Failure>, QuestionnaireId> =
        when (val surveyId = assessment.questionnaireId) {
            null -> nonEmptyListOf(Failure.DataWasExpectedToExist.Survey).left()
            else -> surveyId.right()
        }

    private fun cancelAssessment(assessment: Assessment): Either<NonEmptyList<Failure>, Assessment> =
        assessment.stepForwardTo(Step.Canceled)
            .mapLeft { failure: StepFailure ->
                nonEmptyListOf(
                    when (failure) {
                        StepFailure.InvalidStep -> CancelAssessmentFailure.AssessmentNotCancelable
                    }
                )
            }

    private fun ensureRequesterOwnsAssessment(
        assessment: Assessment,
        requesterId: String
    ): Either<NonEmptyList<Failure>, AssessmentId> =
        requesterOwnsAssessmentRule.ensure(assessment = assessment, requesterId = requesterId)

    private fun ensureRequestIsAuthenticated(): Either<NonEmptyList<Failure>, String> =
        requestIsAuthenticatedRule.ensure()
}


