package tcla.contexts.tcla.core.application.failures


sealed class EnqueueTeamMemberMessageSendingIfNeededFailure: Failure() {
    data class BackgroundException(val exception: Throwable) : EnqueueTeamMemberMessageSendingIfNeededFailure() {
        override val humanReadableSummary: String = "Background exception"
    }
}
