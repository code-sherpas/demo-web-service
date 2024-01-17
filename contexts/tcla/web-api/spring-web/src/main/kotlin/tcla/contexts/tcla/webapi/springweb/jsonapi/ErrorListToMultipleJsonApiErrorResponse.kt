package tcla.contexts.tcla.webapi.springweb.jsonapi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import tcla.libraries.jsonapi.Error
import tcla.libraries.jsonapi.ErrorDocument

fun List<Error>.toMultipleJsonApiErrorResponse(): ResponseEntity<Any> =
    ResponseEntity(ErrorDocument(errors = this), HttpStatus.BAD_REQUEST)
