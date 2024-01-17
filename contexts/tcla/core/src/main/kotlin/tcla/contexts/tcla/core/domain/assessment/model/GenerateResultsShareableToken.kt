package tcla.contexts.tcla.core.domain.assessment.model

fun generateResultsShareableToken(): ResultsShareableToken =
    ResultsShareableToken(
        buildString {
            repeat(256) {
                append(symbolSet.random())
            }
        }
    )

private val symbolSet = (('0'..'9') + ('a'..'z') + ('A'..'Z')).toSet()

