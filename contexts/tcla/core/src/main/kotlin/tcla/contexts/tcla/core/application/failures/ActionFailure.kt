package tcla.contexts.tcla.core.application.failures

sealed class ActionFailure: Failure() {
    data object InvalidDescription : ActionFailure() {
        override val humanReadableSummary: String = "Action description is not valid"
    }
    data object InvalidIsArchived : ActionFailure() {
        override val humanReadableSummary: String = "Action isArchived is not valid"
    }

    data object InvalidTitle : ActionFailure() {
        override val humanReadableSummary: String = "Action title is not valid"
    }

    data object InvalidContext : ActionFailure() {
        override val humanReadableSummary: String = "Action context is not valid"
    }

    data object InvalidChallenges : ActionFailure() {
        override val humanReadableSummary: String = "Action challenges is not valid"
    }

    data object InvalidGoals : ActionFailure() {
        override val humanReadableSummary: String = "Action goals is not valid"
    }

    data object InvalidTargetTclDrivers : ActionFailure() {
        override val humanReadableSummary: String = "Action target TCL drivers is not valid"
    }
    data object InvalidTargetQuestionsToThinkAbout : ActionFailure() {
        override val humanReadableSummary: String = "Action target questions to think about is not valid"
    }
}
