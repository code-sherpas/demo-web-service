package tcla.contexts.tcla.core.application.failures

sealed class InstantToTimestampFailure : Failure() {
    data object InstantIsTooLargeToRepresentAsTimestamp: InstantToTimestampFailure() {
        override val humanReadableSummary: String = ""
    }
}
