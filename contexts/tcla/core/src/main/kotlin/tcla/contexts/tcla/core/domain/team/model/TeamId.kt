package tcla.contexts.tcla.core.domain.team.model

import java.util.UUID

@JvmInline
value class TeamId(val uuid: UUID) {
    override fun toString(): String = uuid.toString()
}
