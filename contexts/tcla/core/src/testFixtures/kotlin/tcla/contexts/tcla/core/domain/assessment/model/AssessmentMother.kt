package tcla.contexts.tcla.core.domain.assessment.model

import tcla.contexts.tcla.core.domain.questionnaire.model.QuestionnaireId
import tcla.contexts.tcla.core.domain.team.model.TeamId
import java.time.Instant
import java.util.TimeZone
import java.util.UUID

object AssessmentMother {
    fun id(value: UUID = UUID.randomUUID()): AssessmentId = AssessmentId(uuid = value)
    fun default(
        id: AssessmentId = AssessmentId(UUID.randomUUID()),
        title: Title = Title("My Assessment"),
        currentStep: Step = Step.Scheduled,
        teamId: TeamId = TeamId(UUID.randomUUID()),
        questionnaireId: QuestionnaireId? = null,
        startedCollectingDataAt: Instant? = null,
        resultsShareableToken: ResultsShareableToken = generateResultsShareableToken(),
        teamName: String = "Avengers",
        teamTimeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Assessment = Assessment(
        id = id,
        title = title,
        currentStep = currentStep,
        teamId = teamId,
        questionnaireId = questionnaireId,
        startedCollectingDataAt = startedCollectingDataAt,
        resultsShareableToken = resultsShareableToken,
        teamName = teamName,
        teamTimeZone = teamTimeZone
    )
}
