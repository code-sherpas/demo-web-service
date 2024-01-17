package tcla.contexts.tcla.core.application.failures

sealed class Failure {
    /**
     * IMPORTANT: the value of this field on each concrete failure
     * MUST NOT CHANGE from occurrence to occurrence
     */
    abstract val humanReadableSummary: String

    data object InvalidDocument : Failure() {
        override val humanReadableSummary: String = "Invalid document"
    }

    data object JsonApiTypeNotAllowed : Failure() {
        override val humanReadableSummary: String = "JSON:API type not allowed"
    }

    data class TechnicalException(val exception: Throwable): Failure() {
        override val humanReadableSummary: String = ""
    }

    sealed class StringIsNotUuid : Failure() {
        override val humanReadableSummary: String = "String is not a UUID"

        data object TeamId : StringIsNotUuid()
        data object AssessmentId : StringIsNotUuid()
        data object SurveyId : StringIsNotUuid()
        data object QuestionToThinkAboutId : StringIsNotUuid()
        data object TclDriverId : StringIsNotUuid()
        data object ActionId : StringIsNotUuid()
        data object TeamMemberId : StringIsNotUuid()
    }

    sealed class GeneratedIdsAreNotDistinct : Failure() {
        override val humanReadableSummary: String = "Generated ids are not distinct"

        data object Question: GeneratedIdsAreNotDistinct()
    }

    data class DatabaseException(val exception: Throwable) : Failure() {
        override val humanReadableSummary: String = "Database exception"
    }

    sealed class RequesterDoesNotOwnResource: Failure() {
        override val humanReadableSummary: String = "Requester does not own the resource"
        data object Assessment : RequesterDoesNotOwnResource()
        data object Team : RequesterDoesNotOwnResource()
        data object TeamOwner : RequesterDoesNotOwnResource()
        data object Action : RequesterDoesNotOwnResource()
        data object Organization : RequesterDoesNotOwnResource()
        data object TeamMember : RequesterDoesNotOwnResource()
    }

    sealed class EntityAlreadyExists: Failure() {
        override val humanReadableSummary: String = "Entity already exists"

        data object Survey: EntityAlreadyExists()
        data object Assessment : EntityAlreadyExists()
        data object Question : EntityAlreadyExists()
        data object Action : EntityAlreadyExists()
        data object Organization : EntityAlreadyExists()
    }

    sealed class EntityNotFound: Failure() {
        override val humanReadableSummary: String = "Entity not found"

        data object Team : EntityNotFound()
        data object Survey : EntityNotFound()
        data object Assessment : EntityNotFound()
        data object TeamMember : EntityNotFound()
        data object Account : EntityNotFound()
        data object Questionnaire : EntityNotFound()
        data object TeamOwner : EntityNotFound()
        data object Question : EntityNotFound()
        data object TclDriver : EntityNotFound()
        data object QuestionToThinkAbout : EntityNotFound()
        data object Action : EntityNotFound()
        data object AccountMessage : EntityNotFound()
        data object TeamMemberMessage : EntityNotFound()
    }

    data object RequestNotAuthenticated : Failure() {
        override val humanReadableSummary: String = "Authentication required"
    }

    sealed class UnableToTransformIntoPersistenceData : Failure() {

        override val humanReadableSummary: String = "Unable to transform into persistence data"
        data object AccountMessage : UnableToTransformIntoPersistenceData()
        data object TeamMemberMessage : UnableToTransformIntoPersistenceData()
    }

    sealed class DataWasExpectedToExist : Failure() {
        override val humanReadableSummary: String = "Data was expected to exist"
        data object Survey : DataWasExpectedToExist()
        data object AnswerOption : DataWasExpectedToExist()
        data object TclDriver : DataWasExpectedToExist()
        data object Assessment : DataWasExpectedToExist()
        data object QuestionToThinkAbout : DataWasExpectedToExist()
    }

    data object FilterIsNeeded : Failure() {
        override val humanReadableSummary: String = "Filter is needed"
    }

    data object NoValuesPresentInFilter : Failure() {
        override val humanReadableSummary: String = "No values present in filter"
    }

    data object FilterValuesNotAllowed : Failure() {
        override val humanReadableSummary: String = "Filter values not allowed"
    }

    data object FilterKeyNotAllowed : Failure() {
        override val humanReadableSummary: String = "Filter key not allowed"
    }

    data object TooManyFilters : Failure() {
        override val humanReadableSummary: String = "There are more filters than allowed amount"
    }

    sealed class UnableToTransformIntoDomainData : Failure() {
        override val humanReadableSummary: String = "Unable to transform into domain data"
        data object TeamMemberMessage : UnableToTransformIntoDomainData()
        data object AccountMessage : UnableToTransformIntoDomainData()
        data object TeamMember : UnableToTransformIntoDomainData()
        data object QuestionLabel : UnableToTransformIntoDomainData()
        data object Action : UnableToTransformIntoDomainData()
    }

    sealed class UnsupportedDatabaseFilter : Failure() {
        override val humanReadableSummary: String = "Unsupported database filter"

        data object Survey: UnsupportedDatabaseFilter()
        data object TeamMember : UnsupportedDatabaseFilter()
        data object Response : UnsupportedDatabaseFilter()
        data object TclDriverScore : UnsupportedDatabaseFilter()
        data object Action : UnsupportedDatabaseFilter()
        data object Organization : UnsupportedDatabaseFilter()
        data object Assessment : UnsupportedDatabaseFilter()
    }
}
