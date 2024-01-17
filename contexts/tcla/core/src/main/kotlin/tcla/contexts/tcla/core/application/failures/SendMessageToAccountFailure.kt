package tcla.contexts.tcla.core.application.failures

sealed class SendMessageToAccountFailure : Failure() {
    data object AlreadySent : SendMessageToAccountFailure() {
        override val humanReadableSummary: String = "Account message already sent"
    }

    data object SendingTooEarly : SendMessageToAccountFailure() {
        override val humanReadableSummary: String = "Account message sending too early"
    }

    data object FindTeamOwner : SendMessageToAccountFailure() {
        override val humanReadableSummary: String = "Failure on finding the team owner"
    }

    data object SendPredefinedEmail : SendMessageToAccountFailure() {
        override val humanReadableSummary: String = "Failure on sending predefined email"
    }
}
