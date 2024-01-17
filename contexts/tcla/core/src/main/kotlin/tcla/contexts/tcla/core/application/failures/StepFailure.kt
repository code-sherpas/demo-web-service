package tcla.contexts.tcla.core.application.failures

sealed class StepFailure : Failure() {
    data object InvalidStep : StepFailure() {
        override val humanReadableSummary: String = ""
    }

}
