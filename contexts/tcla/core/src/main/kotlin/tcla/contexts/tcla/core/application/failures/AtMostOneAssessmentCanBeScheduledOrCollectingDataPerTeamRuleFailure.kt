package tcla.contexts.tcla.core.application.failures

sealed class AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeamRuleFailure: Failure() {
    data object AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeam : AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeamRuleFailure() {
        override val humanReadableSummary: String = "At most one assessment can be scheduled or collecting data per team"
    }
}
