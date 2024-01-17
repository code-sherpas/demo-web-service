package tcla.contexts.tcla.webapi.springweb.jsonapi

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_GATEWAY
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNAUTHORIZED
import tcla.contexts.tcla.core.application.failures.ActionFailure
import tcla.contexts.tcla.core.application.failures.AnalyseTclFailure
import tcla.contexts.tcla.core.application.failures.AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeamRuleFailure
import tcla.contexts.tcla.core.application.failures.CancelAssessmentFailure
import tcla.contexts.tcla.core.application.failures.CreateAssessmentFailure
import tcla.contexts.tcla.core.application.failures.CreateExternalQuestionnaireFailure
import tcla.contexts.tcla.core.application.failures.CreateOrganizationFailure
import tcla.contexts.tcla.core.application.failures.CreateTeamFailure
import tcla.contexts.tcla.core.application.failures.DecideWhetherDataIsAnalysableFailure
import tcla.contexts.tcla.core.application.failures.DeleteTeamFailure
import tcla.contexts.tcla.core.application.failures.DownloadQuestionnaireFillingsFailure
import tcla.contexts.tcla.core.application.failures.DownloadResponsesFailure
import tcla.contexts.tcla.core.application.failures.EnqueueAccountMessageSendingIfNeededFailure
import tcla.contexts.tcla.core.application.failures.EnqueueDecisionWhetherDataIsAnalysableFailure
import tcla.contexts.tcla.core.application.failures.EnqueueQuestionnaireFillingsDownloadIfNeededFailure
import tcla.contexts.tcla.core.application.failures.EnqueueTclAnalysisIfNeededFailure
import tcla.contexts.tcla.core.application.failures.EnqueueTeamMemberMessageSendingIfNeededFailure
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.application.failures.Failure.EntityAlreadyExists
import tcla.contexts.tcla.core.application.failures.InstantToTimestampFailure
import tcla.contexts.tcla.core.application.failures.NotOverlappingQuestionnairesByTeamRuleFailure
import tcla.contexts.tcla.core.application.failures.ResponseAcceptanceIntervalFailure
import tcla.contexts.tcla.core.application.failures.SearchTeamOwnersFailure
import tcla.contexts.tcla.core.application.failures.SearchTeamsFailure
import tcla.contexts.tcla.core.application.failures.SendMessageToAccountFailure
import tcla.contexts.tcla.core.application.failures.SendMessageToTeamMemberFailure
import tcla.contexts.tcla.core.application.failures.StartDataCollectionAtStartDateFailure
import tcla.contexts.tcla.core.application.failures.StartDataCollectionFailure
import tcla.contexts.tcla.core.application.failures.StepFailure
import tcla.contexts.tcla.core.application.failures.StopDataCollectionAtEndDateFailure
import tcla.contexts.tcla.core.application.failures.TeamFailure
import tcla.contexts.tcla.core.application.failures.TimeZoneToDomainFailure

fun Failure.toStatusCode(): HttpStatus = when (this) {
    else -> TODO("Code not present in this demo")
}
