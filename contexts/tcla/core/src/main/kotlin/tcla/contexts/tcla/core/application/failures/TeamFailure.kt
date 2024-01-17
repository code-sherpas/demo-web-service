package tcla.contexts.tcla.core.application.failures

sealed class TeamFailure: Failure() {
    data object InvalidTimeZone : TeamFailure() {
        override val humanReadableSummary: String = "Team time zone is not valid"
    }

    data object InvalidName : TeamFailure() {
        override val humanReadableSummary: String = "Team name is not valid"
    }
}
