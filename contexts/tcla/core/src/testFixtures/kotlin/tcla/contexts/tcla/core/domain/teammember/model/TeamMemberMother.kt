package tcla.contexts.tcla.core.domain.teammember.model

import tcla.contexts.tcla.core.domain.Email
import tcla.contexts.tcla.core.domain.team.model.TeamId
import java.util.UUID

object TeamMemberMother {

    fun default(
        id: TeamMemberId = TeamMemberId(UUID.randomUUID()),
        name: String = "John",
        email: String = "john@example.com",
        teamId: TeamId = TeamId(UUID.randomUUID())
    ): TeamMember = TeamMember(
        id = id,
        name = Name(name).fold({ TODO() },{it}),
        email = Email(email).fold({ TODO() }, {it}),
        teamId = teamId
    )
}
