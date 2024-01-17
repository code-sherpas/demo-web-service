package tcla.contexts.tcla.database.springdatajpa.team

import arrow.core.Either
import arrow.core.NonEmptyList
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.team.model.Team
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.contexts.tcla.core.domain.teamowner.model.TeamOwnerId
import tcla.contexts.tcla.database.springdatajpa.AuditMetadata
import java.util.UUID

@Entity(name = "team")
@Table(name = "team", schema = "tcla")
@EntityListeners(AuditingEntityListener::class)
data class JpaTeam(
    @Id
    val id: UUID,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val timeZone: String,
    @Column(nullable = false)
    val ownerId: String
) {
    @Embedded
    var auditMetadata: AuditMetadata = AuditMetadata()

    fun toDomain(): Either<NonEmptyList<Failure>, Team> =
        Team(
            id = TeamId(id),
            name = name,
            timeZone = timeZone,
            ownerId = TeamOwnerId(ownerId)
        )
}

fun Team.toJpa(): JpaTeam = JpaTeam(
    id = id.uuid,
    name = name.string,
    timeZone = timeZone.id,
    ownerId = ownerId.string
)
