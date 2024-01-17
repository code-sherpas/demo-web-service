package tcla.contexts.tcla.database.springdatajpa.team

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaTeamRepository: JpaRepository<JpaTeam, UUID> {
    fun findAllByOwnerId(ownerId: String): List<JpaTeam>
}
