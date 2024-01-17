package tcla.contexts.tcla.core.application.failures

sealed class ResponseAcceptanceIntervalFailure : Failure() {
    data object InvalidDuration : ResponseAcceptanceIntervalFailure() {
        override val humanReadableSummary: String = "Invalid duration"
    }

    data object UnableModifyStartDateWhenItHasAlreadyStarted : ResponseAcceptanceIntervalFailure() {
        override val humanReadableSummary: String = "Unable modify start date when it has already started"
    }

    data object UnableModifyEndDateWhenItHasAlreadyFinished : ResponseAcceptanceIntervalFailure() {
        override val humanReadableSummary: String = "Unable modify end date when it has already finished"
    }

    data object StartDateMustBeEqualToOrAfterNow : ResponseAcceptanceIntervalFailure() {
        override val humanReadableSummary: String = "Start date must be equal to or after now"
    }

    data object EndDateMustBeEqualToOrAfterNow : ResponseAcceptanceIntervalFailure() {
        override val humanReadableSummary: String = "End date must be equal to or after now"
    }
}
