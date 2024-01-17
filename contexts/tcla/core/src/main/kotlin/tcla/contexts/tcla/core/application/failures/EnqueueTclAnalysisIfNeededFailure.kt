package tcla.contexts.tcla.core.application.failures


sealed class EnqueueTclAnalysisIfNeededFailure : Failure() {

    data class BackgroundException(val exception: Throwable) : EnqueueTclAnalysisIfNeededFailure() {
        override val humanReadableSummary: String = ""
    }
}
