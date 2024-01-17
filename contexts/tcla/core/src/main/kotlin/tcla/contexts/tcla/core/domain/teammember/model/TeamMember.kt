package tcla.contexts.tcla.core.domain.teammember.model

import tcla.contexts.tcla.core.domain.Email
import tcla.contexts.tcla.core.domain.team.model.TeamId

data class TeamMember(
    val id: TeamMemberId,
    val name: Name,
    val email: Email,
    val teamId: TeamId
)
