package tcla.contexts.tcla.core.application.failures

import tcla.libraries.search.ManyValuesFilter

sealed class SearchTeamOwnersFailure : Failure() {

    data object AssessmentNotFound : SearchTeamOwnersFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data class DatabaseException(val exception: Throwable) : SearchTeamOwnersFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object UnsupportedDatabaseFilter : SearchTeamOwnersFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object AtLeastOneFilterRequired : SearchTeamOwnersFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object NoValuesPresentInFilter : SearchTeamOwnersFailure() {
        const val PUBLIC_DESCRIPTION: String = "No values present in filter"
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data class NotAllowedFilterKey(val filterKey: String): SearchTeamOwnersFailure() {
        companion object {
            const val PUBLIC_DESCRIPTION: String = "Not allowed filter key"
        }

        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object TooManyFilters: SearchTeamOwnersFailure() {
        const val PUBLIC_DESCRIPTION: String = "Too many filters"
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data class NotAllowedFilterValues(val filter: ManyValuesFilter<String, String>): SearchTeamOwnersFailure() {
        companion object {
            const val PUBLIC_DESCRIPTION: String = "Not allowed filter values"
        }

        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object AssessmentIdNotConformsUUID : SearchTeamOwnersFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }
}
