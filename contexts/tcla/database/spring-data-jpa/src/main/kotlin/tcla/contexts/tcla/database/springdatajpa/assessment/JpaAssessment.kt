package tcla.contexts.tcla.database.springdatajpa.assessment

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.right
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.model.ResultsShareableToken
import tcla.contexts.tcla.core.domain.assessment.model.Title
import tcla.contexts.tcla.core.domain.questionnaire.model.QuestionnaireId
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.contexts.tcla.database.springdatajpa.AuditMetadata
import tcla.contexts.tcla.database.springdatajpa.questionnaire.JpaQuestionnaire
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeam
import tcla.contexts.tcla.database.springdatajpa.toDomainTimeZone
import java.sql.Timestamp
import java.util.TimeZone
import java.util.UUID

@Entity(name = "assessment")
@Table(name = "assessment", schema = "tcla")
@EntityListeners(AuditingEntityListener::class)
data class JpaAssessment(
    @Id
    val id: UUID,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val currentStep: String,
    @Column(nullable = true)
    val startedCollectingDataAt: Timestamp?,
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", foreignKey = ForeignKey(name = "fk_tcla_team"))
    val team: JpaTeam,
    @OneToOne(optional = true, mappedBy = "assessment", fetch = FetchType.EAGER)
    val questionnaire: JpaQuestionnaire?,
    @Column(nullable = false)
    val resultsShareableToken: String,
    @Column(nullable = false)
    val teamName: String,
    @Column(nullable = false)
    val teamTimeZone: String
) {
    @Embedded
    var auditMetadata: AuditMetadata = AuditMetadata()

    fun toDomain(): Either<NonEmptyList<Failure>, Assessment> =
        teamTimeZone.toDomainTimeZone()
            .flatMap { timeZone: TimeZone ->
                Assessment(
                    id = AssessmentId(id),
                    title = Title(title),
                    teamId = TeamId(team.id),
                    startedCollectingDataAt = startedCollectingDataAt?.toInstant(),
                    currentStep = currentStep.toDomainStep(),
                    questionnaireId = when (questionnaire) {
                        null -> null
                        else -> QuestionnaireId(questionnaire.id)
                    },
                    resultsShareableToken = ResultsShareableToken(resultsShareableToken),
                    teamName = teamName,
                    teamTimeZone = timeZone
                ).right()
            }
}

fun Assessment.toJpa(jpaQuestionnaire: JpaQuestionnaire?, jpaTeam: JpaTeam): JpaAssessment = JpaAssessment(
    id = id.uuid,
    title = title.string,
    team = jpaTeam,
    startedCollectingDataAt = startedCollectingDataAt?.toEpochMilli()?.let(::Timestamp),
    currentStep = currentStep.toJpa(),
    questionnaire = jpaQuestionnaire,
    resultsShareableToken = resultsShareableToken.token,
    teamName = teamName,
    teamTimeZone = teamTimeZone.id
)
