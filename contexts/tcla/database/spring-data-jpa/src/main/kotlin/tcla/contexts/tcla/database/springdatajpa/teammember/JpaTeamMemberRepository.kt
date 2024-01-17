package tcla.contexts.tcla.database.springdatajpa.teammember

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaTeamMemberRepository: JpaRepository<JpaTeamMember, UUID> {
    fun findAllByTeam_Id(teamId: UUID): List<JpaTeamMember>
}
