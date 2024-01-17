package tcla.contexts.tcla.core.application.assessment.update

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.nonEmptyListOf
import arrow.core.right
import jakarta.inject.Named
import org.springframework.beans.factory.annotation.Value
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.RequestIsAuthenticatedRule
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.cancel.CancelAssessment
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.model.Title
import tcla.contexts.tcla.core.domain.assessment.rule.RequesterOwnsAssessmentRule
import tcla.contexts.tcla.core.domain.questionnaire.model.Questionnaire
import tcla.libraries.transactional.IsolationLevel
import tcla.libraries.transactional.TransactionExecutor
import tcla.libraries.uuidvalidation.StringNotConformsUuid
import tcla.libraries.uuidvalidation.toUuid
import java.util.UUID

@Named
class UpdateAssessmentCommandHandler(
    private val transactionExecutor: TransactionExecutor,
    private val requestIsAuthenticatedRule: RequestIsAuthenticatedRule,
    private val requesterOwnsAssessmentRule: RequesterOwnsAssessmentRule,
    private val assessmentRepository: AssessmentRepository,
    @Value("\${web-application.base-url}") private val webApplicationBaseUrl: String,
    private val cancelAssessment: CancelAssessment
) {
    fun execute(command: UpdateAssessmentCommand): Either<NonEmptyList<Failure>, UpdateAssessmentSuccess> =
        transactionExecutor.transactional(isolationLevel = IsolationLevel.REPEATABLE_READ) {
            ensureRequestIsAuthenticated()
                .flatMap { requesterId ->
                    val assessmentFieldsToUpdate = command.fields
                    command.id.toUuid()
                        .mapLeft { _: StringNotConformsUuid -> nonEmptyListOf(Failure.StringIsNotUuid.AssessmentId) }
                        .flatMap { uuid -> ensureAssessmentExists(uuid) }
                        .flatMap { assessmentId -> findAssessment(assessmentId) }
                        .flatMap { assessment -> ensureRequesterOwnsAssessment(assessment, requesterId) }
                        .flatMap { assessment -> updateAssessment(assessment, assessmentFieldsToUpdate) }
                        .flatMap { updatedAssessment ->
                                    ensureNotOverlapsWithAnotherSurvey(updatedAssessment)
                                        .flatMap { updatedQuestionnaire -> saveSurvey(updatedQuestionnaire) }
                        }.flatMap { UpdateAssessmentSuccess.right() }
                }
        }

    private fun ensureRequesterOwnsAssessment(
        assessment: Assessment,
        requesterId: String
    ): Either<NonEmptyList<Failure>, Assessment> =
        requesterOwnsAssessmentRule.ensure(assessment, requesterId)
            .flatMap { assessment.right() }

    private fun ensureRequestIsAuthenticated(): Either<NonEmptyList<Failure>, String> =
        requestIsAuthenticatedRule.ensure()

    private fun ensureNotOverlapsWithAnotherSurvey(
        updatedAssessment: Assessment
    ): Either<NonEmptyList<Failure>, Questionnaire> = TODO("Code not present in this demo")

    private fun saveSurvey(updatedQuestionnaire: Questionnaire): Either<NonEmptyList<Failure>, Questionnaire> =
        TODO("Code not present in this demo")

    private fun findAssessment(assessmentId: AssessmentId): Either<NonEmptyList<Failure>, Assessment> =
        assessmentRepository.find(assessmentId)

    private fun updateAssessment(
        assessment: Assessment,
        assessmentFieldsToUpdate: HashMap<String, String?>
    ): Either<NonEmptyList<Failure>, Assessment> =
        assessment.update(assessmentFieldsToUpdate)
        .flatMap { assessmentRepository.save(it) }

    private fun Assessment.update(
        assessmentFieldsToUpdate: HashMap<String, String?>
    ): Either<NonEmptyList<Failure>, Assessment> =
        updateTitle(assessmentFieldsToUpdate)
            .flatMap { updatedAssessment -> updatedAssessment.updateCurrentStep(assessmentFieldsToUpdate) }

    private fun Assessment.updateTitle(assessmentFieldsToUpdate: HashMap<String, String?>): Either<NonEmptyList<Failure>, Assessment> =
        when (assessmentFieldsToUpdate.containsKey("title")) {
            true -> when (val title = assessmentFieldsToUpdate.title()) {
                null -> TODO("Code not present in this demo")
                else -> updateTitle(Title(title)).right()
            }

            false -> right()
        }

    private fun Assessment.updateCurrentStep(assessmentFieldsToUpdate: HashMap<String, String?>): Either<NonEmptyList<Failure>, Assessment> {
        return when (assessmentFieldsToUpdate.containsKey("currentStep")) {
            true -> when (val currentStep = assessmentFieldsToUpdate.currentStep()) {
                null -> TODO("Code not present in this demo")
                else -> when (currentStep) {
                    "canceled" -> cancelAssessment.execute(this)
                    else -> TODO("Code not present in this demo")
                }
            }

            false -> right()
        }
    }

    private fun ensureAssessmentExists(uuid: UUID): Either<NonEmptyList<Failure>, AssessmentId> =
        assessmentRepository.exists(AssessmentId(uuid))
            .flatMap { assessmentExists ->
                when (assessmentExists) {
                    true -> AssessmentId(uuid).right()
                    false -> TODO("Code not present in this demo")
                }
            }

    private fun HashMap<String, String?>.title(): String? = this["title"]
    private fun HashMap<String, String?>.currentStep(): String? = this["currentStep"]
}
