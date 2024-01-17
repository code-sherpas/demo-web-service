package tcla.contexts.tcla.core.application.assessment.search

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentMother
import java.time.Instant

class SortAssessmentsTest {
    @Test
    fun `assessments with newer startedCollectingDataAt go before others`() {
        val now = Instant.now()
        val assessment1 = AssessmentMother.default(startedCollectingDataAt = now)
        val assessment2 = AssessmentMother.default(startedCollectingDataAt = now.plusSeconds(1))
        val assessments: List<Assessment> = listOf(assessment1, assessment2)

        val expectedAssessments = listOf(assessment2, assessment1)

        SortAssessments().execute(assessments).fold(
            { Assertions.fail("It must be right") },
            { result -> assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAssessments)
            }
        )

    }

    @Test
    fun `assessments with null startedCollectingDataAt go before others`() {
        val now = Instant.now()
        val assessment1 = AssessmentMother.default(startedCollectingDataAt = now)
        val assessment2 = AssessmentMother.default(startedCollectingDataAt = null)
        val assessments: List<Assessment> = listOf(assessment1, assessment2)

        val expectedAssessments = listOf(assessment2, assessment1)

        SortAssessments().execute(assessments).fold(
            { Assertions.fail("It must be right") },
            { result -> assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAssessments)
            }
        )

    }
}
