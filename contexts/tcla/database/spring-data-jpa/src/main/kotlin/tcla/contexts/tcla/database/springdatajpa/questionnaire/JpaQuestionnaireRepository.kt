package tcla.contexts.tcla.database.springdatajpa.questionnaire

import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp
import java.util.UUID

interface JpaQuestionnaireRepository: JpaRepository<JpaQuestionnaire, UUID> {
    fun findAllByAssessment_Id(assessmentId: UUID): List<JpaQuestionnaire>

    fun findAllByAssessment_Team_Id(teamId: UUID): List<JpaQuestionnaire>

    fun findAllByExternalQuestionnaireIdIsNull(): List<JpaQuestionnaire>
    fun findAllByExternalQuestionnaireIdIsNotNullAndStartDateBeforeAndExternalQuestionnaireIsPublic(
        timestamp: Timestamp,
        externalQuestionnaireIsPublic: Boolean
    ): List<JpaQuestionnaire>

    fun findAllByAssessment_Team_IdAndAssessment_CurrentStepIsIn(teamId: UUID, steps: List<String>): List<JpaQuestionnaire>
}
