package tcla.contexts.tcla.core.application.failures

sealed class SendMessageToTeamMemberFailure: Failure() {
    data object AlreadySent : SendMessageToTeamMemberFailure() {
        override val humanReadableSummary: String = "Team member message already sent"
    }

    data object SendingTooEarly : SendMessageToTeamMemberFailure() {
        override val humanReadableSummary: String = "Team member message sending too early"
    }

    data object FindTeamOwner : SendMessageToTeamMemberFailure() {
        override val humanReadableSummary: String = "Failure on finding the team owner"
    }

    data object SendPredefinedEmail : SendMessageToTeamMemberFailure() {
        override val humanReadableSummary: String = "Failure on sending predefined email"
    }
}
