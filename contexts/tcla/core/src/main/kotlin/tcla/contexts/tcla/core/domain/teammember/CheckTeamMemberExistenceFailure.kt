package tcla.contexts.tcla.core.domain.teammember

sealed class CheckTeamMemberExistenceFailure {
    data class DatabaseException(val exception: Throwable): CheckTeamMemberExistenceFailure()
}
