package tcla.contexts.tcla.database.springdatajpa.assessment

import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp
import java.util.UUID

interface JpaAssessmentRepository : JpaRepository<JpaAssessment, UUID> {

    fun existsByResultsShareableToken(token: String): Boolean
    fun findByResultsShareableToken(token: String): JpaAssessment

    fun findAllByCurrentStep(step: String): List<JpaAssessment>
    fun findAllByTeam_Id(teamId: UUID): List<JpaAssessment>

    fun findAllByTeam_OwnerId(ownerId: String): List<JpaAssessment>

    fun findAllByCurrentStepAndQuestionnaire_StartDateBefore(step: String, timestamp: Timestamp): List<JpaAssessment>
    fun findAllByCurrentStepAndQuestionnaire_EndDateBefore(step: String, timestamp: Timestamp): List<JpaAssessment>

    fun findAllByTeam_IdAndCurrentStepIsIn(teamId: UUID, steps: Set<String>): List<JpaAssessment>
}
