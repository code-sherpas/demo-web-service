package tcla.contexts.tcla.webapi.springweb.assessment.update.jsonapi

data class AssessmentResourceForUpdate(
    val type: String,
    val id: String,
    val attributes: Map<String, String?>?
)
