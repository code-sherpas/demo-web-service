package tcla.contexts.tcla.core.domain.assessment.model

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import tcla.contexts.tcla.core.application.failures.StepFailure
import tcla.contexts.tcla.core.domain.questionnaire.model.QuestionnaireId
import tcla.contexts.tcla.core.domain.team.model.TeamId
import java.time.Instant
import java.util.TimeZone

data class Assessment(
    val id: AssessmentId,
    val title: Title,
    val currentStep: Step,
    val teamId: TeamId,
    val questionnaireId: QuestionnaireId?,
    val startedCollectingDataAt: Instant?,
    val resultsShareableToken: ResultsShareableToken,
    val teamName: String,
    val teamTimeZone: TimeZone,
) {
    val isCancelable: Boolean = currentStep in setOf(Step.Scheduled, Step.CollectingData)
    fun stepForwardTo(step: Step): Either<StepFailure, Assessment> =
        currentStep.stepForwardTo(step)
            .flatMap { nextStep -> copy(currentStep = nextStep).right() }
            .flatMap { updatedAssessment ->
                when (updatedAssessment.currentStep) {
                    Step.CollectingData -> updatedAssessment.copy(startedCollectingDataAt = Instant.now())
                    else -> updatedAssessment
                }.right()
            }

    fun updateTitle(newTitle: Title): Assessment = this.copy(title = newTitle)
}
