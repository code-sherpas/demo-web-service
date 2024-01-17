package tcla.contexts.tcla.core.application.assessment.search

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.right
import jakarta.inject.Named
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import java.time.Instant

@Named
class SortAssessments {
    fun execute(assessments: List<Assessment>): Either<NonEmptyList<Failure>, List<Assessment>> =
        assessments.sortedByDescending { assessment -> assessment.startedCollectingDataAt ?: Instant.MAX }.right()
}
