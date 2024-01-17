package tcla.contexts.tcla.core.application.failures



sealed class StartDataCollectionAtStartDateFailure: Failure() {

    data class BackgroundException(val exception: Throwable) : StartDataCollectionAtStartDateFailure() {
        override val humanReadableSummary: String = ""
    }
}
