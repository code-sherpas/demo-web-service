package tcla.contexts.tcla.core.domain.assessment.model

import java.util.UUID


@JvmInline
value class AssessmentId(val uuid: UUID) {
    override fun toString(): String = uuid.toString()
}
