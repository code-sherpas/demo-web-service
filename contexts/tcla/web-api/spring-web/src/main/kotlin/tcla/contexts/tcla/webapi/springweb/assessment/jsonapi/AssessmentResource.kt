package tcla.contexts.tcla.webapi.springweb.assessment.jsonapi

import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.Step

data class AssessmentResource(
    val id: String,
    val attributes: AssessmentResourceAttributes,
) {
    val type: String = ASSESSMENT_JSON_API_TYPE
}

private const val scheduledString = "scheduled"
private const val collectingDataString = "collecting-data"
private const val dataCollectedString = "data-collected"
private const val resultsAvailableString = "results-available"
private const val dataNotAnalysableString = "data-not-analysable"
private const val analysableDataString = "analysable-data"
private const val canceledString = "canceled"

fun Assessment.toResource(): AssessmentResource =
    AssessmentResource(
        id = id.uuid.toString(),
        attributes = AssessmentResourceAttributes(
            questionnaireId = questionnaireId(),
            teamId = teamId.toString(),
            title = title.string,
            currentStep = when(currentStep) {
                Step.AnalysableData -> analysableDataString
                Step.CollectingData -> collectingDataString
                Step.DataCollected -> dataCollectedString
                Step.DataNotAnalysable -> dataNotAnalysableString
                Step.ResultsAvailable -> resultsAvailableString
                Step.Scheduled -> scheduledString
                Step.Canceled -> canceledString
            },
            isCancelable = isCancelable
        )
    )

private fun Assessment.questionnaireId() =
    when (val questionnaireId = questionnaireId) {
        null -> null
        else -> questionnaireId.toString()
    }
