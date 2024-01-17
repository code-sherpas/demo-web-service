package tcla.contexts.tcla.core.domain.questionnaire.model

import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId

data class Questionnaire(
    val id: QuestionnaireId,
    val externalQuestionnaireIsPublic: Boolean,
    val assessmentId: AssessmentId,
)
