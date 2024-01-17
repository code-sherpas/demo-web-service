package tcla.contexts.tcla.webapi.springweb.assessment.find

import arrow.core.flatMap
import arrow.core.right
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import tcla.contexts.tcla.core.application.assessment.find.FindAssessmentQuery
import tcla.contexts.tcla.core.application.assessment.find.FindAssessmentQueryHandler
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.webapi.springweb.assessment.find.jsonapi.AssessmentDocument
import tcla.contexts.tcla.webapi.springweb.assessment.jsonapi.toResource
import tcla.contexts.tcla.webapi.springweb.jsonapi.toFailureResponse

@RestController
class FindAssessmentController(
    private val findAssessmentQueryHandler: FindAssessmentQueryHandler
) {
    @GetMapping("/assessments/{id}", produces = ["application/vnd.api+json"])
    fun execute(
        @PathVariable id: String
    ): ResponseEntity<Any> = findAssessmentQueryHandler.execute(FindAssessmentQuery(id))
        .flatMap { querySuccess -> querySuccess.assessment.right() }
        .fold({ it.toFailureResponse() }, { it.toSuccessResponse() })

    private fun Assessment.toSuccessResponse(): ResponseEntity<Any> =
        AssessmentDocument(data = this.toResource()).let { ResponseEntity.ok(it) }
}
