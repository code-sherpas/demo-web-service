package tcla.contexts.tcla.core.application.assessment.search

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.mapOrAccumulate
import arrow.core.nonEmptyListOf
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.RequestIsAuthenticatedRule
import tcla.contexts.tcla.core.domain.assessment.AssessmentFilterKey
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.libraries.search.ManyValuesFilter
import tcla.libraries.search.OneValueFilter
import tcla.libraries.transactional.IsolationLevel
import tcla.libraries.transactional.TransactionExecutor
import tcla.libraries.uuidvalidation.StringNotConformsUuid
import tcla.libraries.uuidvalidation.toUuid
import java.util.UUID

@Named
class SearchAssessmentsQueryHandler(
    private val assessmentRepository: AssessmentRepository,
    private val transactionExecutor: TransactionExecutor,
    private val requestIsAuthenticatedRule: RequestIsAuthenticatedRule,
    private val sortAssessments: SortAssessments
) {
    fun execute(query: SearchAssessmentsQuery): Either<NonEmptyList<Failure>, SearchAssessmentsSuccess> =
        transactionExecutor.transactional(isolationLevel = IsolationLevel.READ_COMMITTED) {
            requestIsAuthenticatedRule.ensure()
                .flatMap { requesterId ->
                    query.filters.ensureFiltersAreSupported(requesterId)
                        .flatMap { filter ->
                            assessmentRepository.search(filter)
                        }
                }.flatMap { assessments -> sortAssessments.execute(assessments) }
        }.flatMap { assessments -> SearchAssessmentsSuccess(assessments).right() }

    private fun List<ManyValuesFilter<String, String>>.ensureFiltersAreSupported(requesterId: String): Either<NonEmptyList<Failure>, OneValueFilter<AssessmentFilterKey, out Any>?> =
        TODO("Code not present in this demo")
}
