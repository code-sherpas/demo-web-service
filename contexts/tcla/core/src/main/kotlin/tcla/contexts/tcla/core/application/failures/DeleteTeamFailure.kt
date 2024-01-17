package tcla.contexts.tcla.core.application.failures

sealed class DeleteTeamFailure : Failure() {
    data class DatabaseException(val exception: Throwable) : DeleteTeamFailure() {
        override val humanReadableSummary: String = "Database exception"
    }

    data object IdNotConformsUuid : DeleteTeamFailure() {
        override val humanReadableSummary: String = "Team id is not a UUID"
    }

    data object RequesterNotOwnsTeam : DeleteTeamFailure() {
        override val humanReadableSummary: String = "You don't own the team"
    }

    data object TeamNotFound : DeleteTeamFailure() {
        override val humanReadableSummary: String = "Team was not found"
    }
}
