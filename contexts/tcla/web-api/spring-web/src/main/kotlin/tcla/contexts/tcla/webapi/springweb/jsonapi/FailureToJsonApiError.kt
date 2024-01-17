package tcla.contexts.tcla.webapi.springweb.jsonapi

import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.application.failures.FailureClassAndCodeMapping
import tcla.libraries.jsonapi.Error

fun toJsonApiError(failure: Failure): Error = Error(
    status = failure.toStatusCode().value().toString(),
    code = "${FailureClassAndCodeMapping.getCodeFor(failure::class)}",
    title = failure.humanReadableSummary
)
