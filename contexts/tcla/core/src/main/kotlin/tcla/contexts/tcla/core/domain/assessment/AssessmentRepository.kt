package tcla.contexts.tcla.core.domain.assessment

import arrow.core.Either
import arrow.core.NonEmptyList
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.model.ResultsShareableToken
import tcla.contexts.tcla.core.domain.assessment.model.Step
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.libraries.search.Filter
import java.time.Instant

interface AssessmentRepository {
    fun exists(id: AssessmentId): Either<NonEmptyList<Failure>, Boolean>
    fun existsByResultsShareableToken(resultsShareableToken: ResultsShareableToken): Either<NonEmptyList<Failure>, Boolean>
    fun findByResultsShareableToken(resultsShareableToken: ResultsShareableToken): Either<NonEmptyList<Failure>, Assessment>
    fun find(id: AssessmentId): Either<NonEmptyList<Failure>, Assessment>
    fun save(assessment: Assessment): Either<NonEmptyList<Failure>, Assessment>
    fun search(filter: Filter<AssessmentFilterKey>? = null): Either<NonEmptyList<Failure>, List<Assessment>>
    fun searchByCurrentStepAndQuestionnaire_StartDateBefore(step: Step, instant: Instant): Either<NonEmptyList<Failure>, List<Assessment>>
    fun searchByCurrentStepAndQuestionnaire_EndDateBefore(step: Step, instant: Instant): Either<NonEmptyList<Failure>, List<Assessment>>
    fun searchByCurrentStep(step: Step): Either<NonEmptyList<Failure>, List<Assessment>>
    fun searchByTeam_IdAndCurrentStepIsIn(teamId: TeamId, steps: Set<Step>): Either<NonEmptyList<Failure>, List<Assessment>>
}
