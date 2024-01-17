package tcla.contexts.tcla.core.application.failures

import tcla.libraries.search.ManyValuesFilter

sealed class SearchTeamsFailure : Failure() {

    data object RequestNotAuthenticated : SearchTeamsFailure() {
        override val humanReadableSummary: String = ""
    }

    data object Unauthorized : SearchTeamsFailure() {
        override val humanReadableSummary: String = ""
    }
    data object NoValuesPresentInFilter : SearchTeamsFailure() {
        override val humanReadableSummary: String = "No values present in filter"
    }

    data class NotAllowedFilterKey(val filterKey: String): SearchTeamsFailure() {
        override val humanReadableSummary: String = "Not allowed filter key"
    }

    data class NotAllowedFilterValues(val filter: ManyValuesFilter<String, String>): SearchTeamsFailure() {
        override val humanReadableSummary: String = "Not allowed filter values"
    }

    data object UnsupportedDatabaseFilter : SearchTeamsFailure() {
        override val humanReadableSummary: String = "Unsupported query filter"
    }
    data class DatabaseException(val exception: Throwable) : SearchTeamsFailure() {
        override val humanReadableSummary: String = "Database exception"
    }
}
