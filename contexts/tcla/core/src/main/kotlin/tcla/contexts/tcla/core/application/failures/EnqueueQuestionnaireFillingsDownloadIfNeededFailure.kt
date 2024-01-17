package tcla.contexts.tcla.core.application.failures


sealed class EnqueueQuestionnaireFillingsDownloadIfNeededFailure : Failure() {

    data class BackgroundException(val exception: Throwable) : EnqueueQuestionnaireFillingsDownloadIfNeededFailure() {
        override val humanReadableSummary: String = ""
    }
}
