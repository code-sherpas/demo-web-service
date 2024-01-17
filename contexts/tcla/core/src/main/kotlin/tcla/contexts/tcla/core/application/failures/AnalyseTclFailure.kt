package tcla.contexts.tcla.core.application.failures

sealed class AnalyseTclFailure: Failure() {
    data object ThereAreNoResponses : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data class DatabaseException(val exception: Throwable) : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object AssessmentNotFound : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object QuestionnaireNotFound : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object FilterNotSupported : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object InsufficientCells : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object InsufficientRows : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object InvalidURL : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object TclModelCallException : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object TclModelRun : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object UnableToCreateWorkbook : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object UnableToGetSheet : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object UnsupportedData : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object TclDriverScoreAlreadyExist : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object TclDriverNotFound : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object MissingQuestionnaire : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }

    data object AssessmentIsNotInAppropriateStep : AnalyseTclFailure() {
        override val humanReadableSummary: String
            get() = TODO("Not yet implemented")
    }
}
