package tcla.contexts.tcla.core.application.failures

sealed class DownloadQuestionnaireFillingsFailure: Failure() {
    data object ExternalQuestionnaireIdIsNull : DownloadQuestionnaireFillingsFailure() {
        override val humanReadableSummary: String = ""
    }

    data object AssessmentIsNotInAppropriateStep : DownloadQuestionnaireFillingsFailure() {
        override val humanReadableSummary: String = ""
    }

    data object QuestionnaireNotFound : DownloadQuestionnaireFillingsFailure() {
        override val humanReadableSummary: String = ""
    }
}
