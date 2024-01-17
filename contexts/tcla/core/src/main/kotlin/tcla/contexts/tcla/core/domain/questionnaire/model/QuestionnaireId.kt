package tcla.contexts.tcla.core.domain.questionnaire.model

import java.util.UUID


@JvmInline
value class QuestionnaireId(val uuid: UUID) {
    override fun toString(): String = uuid.toString()
}
