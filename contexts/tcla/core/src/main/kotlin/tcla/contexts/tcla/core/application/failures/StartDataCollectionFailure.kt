package tcla.contexts.tcla.core.application.failures

sealed class StartDataCollectionFailure: Failure() {

    data object ExternalQuestionnaireAlreadyExists : StartDataCollectionFailure() {
        override val humanReadableSummary: String = ""
    }
    data object AssessmentIsNotInAppropriateStep : StartDataCollectionFailure() {
        override val humanReadableSummary: String = ""
    }
    data object QuestionnaireHasNotStartedYet : StartDataCollectionFailure() {
        override val humanReadableSummary: String = ""
    }
}
