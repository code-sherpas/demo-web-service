package tcla.contexts.tcla.core.application.failures

sealed class CancelAssessmentFailure : Failure() {

    data object AssessmentNotCancelable : CancelAssessmentFailure() {
        override val humanReadableSummary: String = "Assessment is not cancelable"
    }
}
