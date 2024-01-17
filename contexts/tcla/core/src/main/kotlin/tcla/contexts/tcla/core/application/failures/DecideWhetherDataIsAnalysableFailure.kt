package tcla.contexts.tcla.core.application.failures


sealed class DecideWhetherDataIsAnalysableFailure : Failure() {
    data object AssessmentIsNotInAppropriateStep : DecideWhetherDataIsAnalysableFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }
}
