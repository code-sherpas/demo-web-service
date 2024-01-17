package tcla.contexts.tcla.core.application.assessment.update

data class UpdateAssessmentCommand(
    val id: String,
    val fields: HashMap<String, String?>
)
