package tcla.contexts.tcla.webapi.springweb.jsonapi

import tcla.contexts.tcla.core.application.failures.Failure
import tcla.libraries.jsonapi.ErrorDocument

fun Failure.toSingleJsonApiErrorDocument(): ErrorDocument =
    ErrorDocument(errors = listOf(toJsonApiError(this)))
