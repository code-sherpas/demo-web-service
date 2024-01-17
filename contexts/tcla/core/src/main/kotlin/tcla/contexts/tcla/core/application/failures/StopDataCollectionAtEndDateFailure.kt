package tcla.contexts.tcla.core.application.failures


sealed class StopDataCollectionAtEndDateFailure: Failure() {

    data class BackgroundException(val exception: Throwable) : StopDataCollectionAtEndDateFailure() {
        override val humanReadableSummary: String = ""
    }
}
