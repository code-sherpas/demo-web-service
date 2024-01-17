package tcla.contexts.tcla.core.application.failures

sealed class TimeZoneToDomainFailure : Failure() {
    data object UnknownTimeZone : TimeZoneToDomainFailure() {
        override val humanReadableSummary: String = "Unable to transform time zone into domain data"
    }
}
