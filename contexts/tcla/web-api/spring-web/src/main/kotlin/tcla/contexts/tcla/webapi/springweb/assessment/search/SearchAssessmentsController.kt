package tcla.contexts.tcla.webapi.springweb.assessment.search


import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.right
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcla.contexts.tcla.core.application.assessment.search.SearchAssessmentsQuery
import tcla.contexts.tcla.core.application.assessment.search.SearchAssessmentsQueryHandler
import tcla.contexts.tcla.core.application.assessment.search.SearchAssessmentsSuccess
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.webapi.springweb.assessment.jsonapi.toResource
import tcla.contexts.tcla.webapi.springweb.assessment.search.jsonapi.AssessmentsDocument
import tcla.contexts.tcla.webapi.springweb.jsonapi.toFailureResponse
import tcla.libraries.jsonapi.buildFilters


@RestController
class SearchAssessmentsController(
    private val searchAssessmentsQueryHandler: SearchAssessmentsQueryHandler
) {

    @GetMapping("/assessments", produces = ["application/vnd.api+json"])
    fun execute(
        @RequestParam(
            name = "filter[team]",
            required = false
        ) teamFilterValue: String?,
        @RequestParam(
            name = "filter[owner]",
            required = false
        ) ownerFilterValue: String?
    ): ResponseEntity<Any> =
        buildFilters(listOf(Pair("team", teamFilterValue), Pair("owner", ownerFilterValue)))
            .let { filters ->
                search(SearchAssessmentsQuery(filters))
                    .flatMap { searchAssessmentsSuccess -> searchAssessmentsSuccess.assessments.right() }
                    .fold(ifLeft = { it.toFailureResponse() }, ifRight = { it.toSuccessResponse() })
            }

    private fun search(query: SearchAssessmentsQuery): Either<NonEmptyList<Failure>, SearchAssessmentsSuccess> =
        searchAssessmentsQueryHandler.execute(query)

    private fun List<Assessment>.toSuccessResponse(): ResponseEntity<Any> =
        AssessmentsDocument(this.map { assessment -> assessment.toResource() })
            .let { ResponseEntity.ok(it) }
}
