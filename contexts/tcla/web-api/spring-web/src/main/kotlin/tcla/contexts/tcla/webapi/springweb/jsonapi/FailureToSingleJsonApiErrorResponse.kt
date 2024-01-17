package tcla.contexts.tcla.webapi.springweb.jsonapi

import org.springframework.http.ResponseEntity
import tcla.contexts.tcla.core.application.failures.Failure

fun Failure.toSingleJsonApiErrorResponse(): ResponseEntity<Any> =
    ResponseEntity(toSingleJsonApiErrorDocument(), toStatusCode())
