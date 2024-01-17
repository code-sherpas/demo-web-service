package tcla.contexts.tcla.webapi.springweb.jsonapi

import arrow.core.NonEmptyList
import org.springframework.http.ResponseEntity
import tcla.contexts.tcla.core.application.failures.Failure

fun NonEmptyList<Failure>.toFailureResponse(): ResponseEntity<Any> =
    map(::toJsonApiError).toMultipleJsonApiErrorResponse()
