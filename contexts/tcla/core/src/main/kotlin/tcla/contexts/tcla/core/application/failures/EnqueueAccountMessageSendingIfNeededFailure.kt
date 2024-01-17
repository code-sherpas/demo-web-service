package tcla.contexts.tcla.core.application.failures


sealed class EnqueueAccountMessageSendingIfNeededFailure: Failure() {
    data class BackgroundException(val exception: Throwable) : EnqueueAccountMessageSendingIfNeededFailure() {
        override val humanReadableSummary: String = "Background exception"
    }
}
