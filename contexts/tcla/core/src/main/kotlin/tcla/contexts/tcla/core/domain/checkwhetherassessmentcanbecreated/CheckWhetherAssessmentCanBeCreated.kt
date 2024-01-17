package tcla.contexts.tcla.core.domain.checkwhetherassessmentcanbecreated

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.Step
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.libraries.transactional.IsolationLevel
import tcla.libraries.transactional.TransactionExecutor

@Named
class CheckWhetherAssessmentCanBeCreated(
    private val transactionExecutor: TransactionExecutor,
    private val assessmentRepository: AssessmentRepository
) {
    fun execute(teamId: TeamId): Either<NonEmptyList<Failure>, Boolean> =
        transactionExecutor.transactional(isolationLevel = IsolationLevel.READ_COMMITTED) {
            searchAssessments(teamId)
                .flatMap { assessments ->
                    when (assessments.size) {
                        0 -> true.right()
                        else -> false.right()
                    }
                }
        }

    private fun searchAssessments(teamId: TeamId): Either<NonEmptyList<Failure>, List<Assessment>> =
        assessmentRepository.searchByTeam_IdAndCurrentStepIsIn(teamId, setOf(Step.Scheduled, Step.CollectingData))
}
