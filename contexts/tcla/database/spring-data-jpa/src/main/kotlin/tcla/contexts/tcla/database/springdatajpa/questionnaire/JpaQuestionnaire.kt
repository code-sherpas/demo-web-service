package tcla.contexts.tcla.database.springdatajpa.questionnaire

import arrow.core.Either
import arrow.core.NonEmptyList
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.questionnaire.model.Questionnaire
import tcla.contexts.tcla.database.springdatajpa.AuditMetadata
import tcla.contexts.tcla.database.springdatajpa.assessment.JpaAssessment
import java.sql.Timestamp
import java.util.UUID

@Entity(name = "questionnaire")
@Table(name = "survey", schema = "tcla")
@EntityListeners(AuditingEntityListener::class)
data class JpaQuestionnaire(
    @Id val id: UUID,
    @Column(nullable = false)
    val startDate: Timestamp,
    @Column(name = "\"end_date\"", nullable = false)
    val endDate: Timestamp,
    @Column(nullable = true)
    val externalQuestionnaireId: String?,
    @Column(nullable = false)
    val externalQuestionnaireIsPublic: Boolean,
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_id", foreignKey = ForeignKey(name = "fk_tcla_assessment"))
    val assessment: JpaAssessment,
    @Column(nullable = false)
    val maximumAmountToBeCollected: Int,
    @Column(nullable = false)
    val minimumRateRequired: Double
) {
    @Embedded
    var auditMetadata: AuditMetadata = AuditMetadata()

    fun toDomain(): Either<NonEmptyList<Failure>, Questionnaire> =
        TODO("Code not present in this demo")
}

fun Questionnaire.toJpa(jpaAssessment: JpaAssessment): JpaQuestionnaire =
    TODO("Code not present in this demo")
