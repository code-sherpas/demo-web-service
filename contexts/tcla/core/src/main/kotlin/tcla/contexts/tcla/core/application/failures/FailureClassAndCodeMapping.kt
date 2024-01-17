package tcla.contexts.tcla.core.application.failures

import kotlin.reflect.KClass


object FailureClassAndCodeMapping {
    private const val CONTEXT_CODE_PREFIX = "1"

    val associations: List<FailureClassAndCodeAssociation> = listOf(
        FailureClassAndCodeAssociation(Failure.InvalidDocument::class, 1),
        FailureClassAndCodeAssociation(Failure.JsonApiTypeNotAllowed::class, 2),
        FailureClassAndCodeAssociation(CreateAssessmentFailure.TeamIsNotBigEnough::class, 3),
        FailureClassAndCodeAssociation(TimeZoneToDomainFailure.UnknownTimeZone::class, 4),
        // 5
        // 6
        // 7
        // 8
        // 9
        // 10
        // 11
        FailureClassAndCodeAssociation(NotOverlappingQuestionnairesByTeamRuleFailure.OverlapsWithAnotherAssessment::class, 12),
        // 13
        // 14
        // 15
        // 16
        // 17
        // 18
        // 19
        // 20
        FailureClassAndCodeAssociation(Failure.StringIsNotUuid.TeamId::class, 21),
        // 22
        FailureClassAndCodeAssociation(Failure.RequestNotAuthenticated::class, 23),
        // 24
        FailureClassAndCodeAssociation(Failure.DatabaseException::class, 25),
        FailureClassAndCodeAssociation(Failure.UnableToTransformIntoDomainData.TeamMember::class, 26),
        // 27
        // 28
        // 29
        // 30
        // 31
        FailureClassAndCodeAssociation(DeleteTeamFailure.RequesterNotOwnsTeam::class, 32),
        FailureClassAndCodeAssociation(DeleteTeamFailure.DatabaseException::class, 33),
        FailureClassAndCodeAssociation(DeleteTeamFailure.IdNotConformsUuid::class, 34),
        FailureClassAndCodeAssociation(Failure.EntityNotFound.Assessment::class, 35),
        FailureClassAndCodeAssociation(DeleteTeamFailure.TeamNotFound::class, 36),
        FailureClassAndCodeAssociation(AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeamRuleFailure.AtMostOneAssessmentCanBeScheduledOrCollectingDataPerTeam::class, 37),
        FailureClassAndCodeAssociation(Failure.EntityNotFound.Team::class, 38),
        FailureClassAndCodeAssociation(Failure.EntityNotFound.TeamMember::class, 39),
        FailureClassAndCodeAssociation(Failure.EntityNotFound.Account::class, 40),
        FailureClassAndCodeAssociation(Failure.EntityNotFound.Survey::class, 41),
        FailureClassAndCodeAssociation(Failure.UnsupportedDatabaseFilter::class, 42)
    )

    fun getCodeFor(clazz: KClass<*>): String? = associations
        .firstOrNull { association -> association.clazz == clazz }
        ?.code
        ?.let { code: Int -> CONTEXT_CODE_PREFIX.plus(code.toString()) }

}

data class FailureClassAndCodeAssociation(val clazz: KClass<out Failure>, val code: Int)


