package tcla.contexts.tcla.core.application.failures

sealed class CreateOrganizationFailure : Failure() {
    data object CurrentPlanNotAllows : CreateOrganizationFailure() {
        override val humanReadableSummary: String = "Current does not allow this action"
    }
}
