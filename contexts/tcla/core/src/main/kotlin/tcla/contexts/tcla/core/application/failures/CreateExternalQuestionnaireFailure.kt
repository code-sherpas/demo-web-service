package tcla.contexts.tcla.core.application.failures

sealed class CreateExternalQuestionnaireFailure: Failure() {
    data object InvalidUrl : CreateExternalQuestionnaireFailure() {
        override val humanReadableSummary: String = ""
    }

    data object LocationHeaderNotPresent : CreateExternalQuestionnaireFailure() {
        override val humanReadableSummary: String = ""
    }
    data class RequestNotExecuted(val exception: Throwable): CreateExternalQuestionnaireFailure() {
        override val humanReadableSummary: String = ""
    }
    data class ErrorResponse(val code: Int, val body: String?) : CreateExternalQuestionnaireFailure() {
        override val humanReadableSummary: String = ""
    }
}
