package tcla.contexts.tcla.core.application.failures

sealed class CreateTeamFailure: Failure() {
    data object CurrentPlanNotAllows : CreateTeamFailure() {
        override val humanReadableSummary: String = ""
    }
}
