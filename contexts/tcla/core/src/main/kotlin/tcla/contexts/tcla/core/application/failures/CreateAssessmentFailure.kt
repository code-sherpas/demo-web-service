package tcla.contexts.tcla.core.application.failures

sealed class CreateAssessmentFailure: Failure() {
    data object InvalidStartDateFormat : CreateAssessmentFailure() {
        override val humanReadableSummary: String = ""
    }

    data object InvalidEndDateFormat : CreateAssessmentFailure() {
        override val humanReadableSummary: String = ""
    }

    data object StartDateMustBeNowOrAfter : CreateAssessmentFailure() {
        override val humanReadableSummary: String = ""
    }

    data object ResultsShareableTokenAlreadyExists : CreateAssessmentFailure() {
        override val humanReadableSummary: String = ""
    }

    data object TeamIsNotBigEnough : CreateAssessmentFailure() {
        override val humanReadableSummary: String = "Team is not big enough"
    }

}
