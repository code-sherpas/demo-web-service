package tcla.contexts.tcla.database.springdatajpa.assessment

import tcla.contexts.tcla.core.domain.assessment.model.Step

private const val scheduledString = "scheduled"
private const val collectingDataString = "collecting-data"
private const val dataCollectedString = "data-collected"
private const val resultsAvailableString = "results-available"
private const val dataNotAnalysableString = "data-not-analysable"
private const val analysableDataString = "analysable-data"
private const val canceledString = "canceled"

fun Step.toJpa(): String = when (this) {
    Step.CollectingData -> collectingDataString
    Step.DataCollected -> dataCollectedString
    Step.DataNotAnalysable -> dataNotAnalysableString
    Step.ResultsAvailable -> resultsAvailableString
    Step.Scheduled -> scheduledString
    Step.AnalysableData -> analysableDataString
    Step.Canceled -> canceledString
}

fun String.toDomainStep(): Step = when (this) {
    scheduledString -> Step.Scheduled
    collectingDataString -> Step.CollectingData
    dataCollectedString -> Step.DataCollected
    resultsAvailableString -> Step.ResultsAvailable
    dataNotAnalysableString -> Step.DataNotAnalysable
    analysableDataString -> Step.AnalysableData
    canceledString -> Step.Canceled
    else -> TODO()
}
