package tcla.contexts.tcla.core.application.failures

sealed class DownloadResponsesFailure: Failure() {
    data class RequestNotExecuted(val exception: Throwable) : DownloadResponsesFailure() {
        override val humanReadableSummary: String = ""
    }

    data object InvalidUrl : DownloadResponsesFailure() {
        override val humanReadableSummary: String = ""
    }
    data object UnexpectedResponse : DownloadResponsesFailure() {
        override val humanReadableSummary: String = ""
    }
    data class ResponseError(val code: Int, val body: String?) : DownloadResponsesFailure() {
        override val humanReadableSummary: String = ""
    }
}
