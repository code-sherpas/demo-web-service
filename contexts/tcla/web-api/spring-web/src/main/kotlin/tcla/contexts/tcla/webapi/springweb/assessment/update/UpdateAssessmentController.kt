package tcla.contexts.tcla.webapi.springweb.assessment.update

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.application.assessment.update.UpdateAssessmentCommand
import tcla.contexts.tcla.core.application.assessment.update.UpdateAssessmentCommandHandler
import tcla.contexts.tcla.core.application.assessment.update.UpdateAssessmentSuccess
import tcla.contexts.tcla.webapi.springweb.assessment.jsonapi.ASSESSMENT_JSON_API_TYPE
import tcla.contexts.tcla.webapi.springweb.assessment.update.jsonapi.AssessmentPatchRequestDocument
import tcla.contexts.tcla.webapi.springweb.jsonapi.toFailureResponse
import tcla.libraries.jsonserialization.deserialization.deserializeJsonAs


private val ALLOWED_JSON_API_TYPES = setOf(ASSESSMENT_JSON_API_TYPE)

@RestController
class UpdateAssessmentController(
    private val updateAssessmentCommandHandler: UpdateAssessmentCommandHandler
) {

    @PatchMapping("/assessments/{assessmentId}")
    fun execute(
        @PathVariable assessmentId: String,
        @RequestBody requestBody: String
    ): ResponseEntity<Any> = requestBody.deserializeJsonAs(AssessmentPatchRequestDocument::class)
        .mapLeft { TODO("Code not present in this demo") }
        .flatMap { document -> ensureAllowedJsonApiType(document) }
        .flatMap { document ->
            when (document.data.attributes) {
                null -> mapOf()
                else -> document.data.attributes
            }.let { map: Map<String, String?> ->
                executeCommand(document.data.id, map)
                    .mapLeft { failures -> failures.toFailureResponse() }
                    .flatMap { successResponse().right() }
            }
        }.fold({ it }, { it })

    private fun ensureAllowedJsonApiType(document: AssessmentPatchRequestDocument): Either<Nothing, AssessmentPatchRequestDocument> =
        either {
            ensure(ALLOWED_JSON_API_TYPES.contains(document.data.type)) { TODO("Code not present in this demo") }
            document
        }

    private fun executeCommand(
        id: String,
        attributes: Map<String, String?>
    ): Either<NonEmptyList<Failure>, UpdateAssessmentSuccess> =
        UpdateAssessmentCommand(
            id = id,
            fields = HashMap(attributes)
        ).let { command ->
            updateAssessmentCommandHandler.execute(command)
        }

    private fun successResponse(): ResponseEntity<Any> = ResponseEntity.noContent().build()
}
