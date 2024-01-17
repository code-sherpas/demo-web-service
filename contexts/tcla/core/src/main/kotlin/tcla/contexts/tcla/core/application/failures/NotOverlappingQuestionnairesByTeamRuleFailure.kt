package tcla.contexts.tcla.core.application.failures

sealed class NotOverlappingQuestionnairesByTeamRuleFailure: Failure() {
    data object OverlapsWithAnotherAssessment : NotOverlappingQuestionnairesByTeamRuleFailure() {
        override val humanReadableSummary: String = "This assessment overlaps with another assessment of the same team"
    }
}
