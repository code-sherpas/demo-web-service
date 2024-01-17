package tcla.contexts.tcla.database.springdatajpa.teammember

import arrow.core.flatMap
import arrow.core.right
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.contexts.tcla.core.domain.Email
import tcla.contexts.tcla.core.domain.teammember.model.Name
import tcla.contexts.tcla.core.domain.teammember.model.TeamMember
import tcla.contexts.tcla.core.domain.teammember.model.TeamMemberId
import tcla.contexts.tcla.database.springdatajpa.AuditMetadata
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeam
import java.util.UUID

@Entity(name = "team_member")
@Table(name = "team_member", schema = "tcla")
@EntityListeners(AuditingEntityListener::class)
data class JpaTeamMember(
    @Id
    val id: UUID,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val email: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", foreignKey = ForeignKey(name = "fk_tcla_team"))
    val team: JpaTeam
) {
    @Embedded
    var auditMetadata: AuditMetadata = AuditMetadata()

    fun toDomain(): TeamMember =
        Name(name)
            .mapLeft { _: Name.InvalidName -> TODO("Code not present in this demo") }
            .flatMap { domainName ->
                Email(email)
                    .mapLeft { _: Email.InvalidEmail -> TODO("Code not present in this demo") }
                    .flatMap { domainEmail ->
                        TeamMember(
                            id = TeamMemberId(id),
                            name = domainName,
                            email = domainEmail,
                            teamId = TeamId(team.id)
                        ).right()
                    }
            }.fold({ TODO("Code not present in this demo") }, { it })
}

fun TeamMember.toJpa(jpaTeam: JpaTeam): JpaTeamMember {
    return JpaTeamMember(
        id = id.uuid,
        name = name.string,
        email = email.string,
        team = jpaTeam
    )
}
