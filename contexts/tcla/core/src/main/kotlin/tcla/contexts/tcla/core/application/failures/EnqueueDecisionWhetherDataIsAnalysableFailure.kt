package tcla.contexts.tcla.core.application.failures


sealed class EnqueueDecisionWhetherDataIsAnalysableFailure : Failure() {

    data class BackgroundException(val exception: Throwable) : EnqueueDecisionWhetherDataIsAnalysableFailure() {
        override val humanReadableSummary: String = ""
    }
}
