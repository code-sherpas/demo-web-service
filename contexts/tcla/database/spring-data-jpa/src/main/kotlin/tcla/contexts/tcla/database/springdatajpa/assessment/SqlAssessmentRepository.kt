package tcla.contexts.tcla.database.springdatajpa.assessment

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.left
import arrow.core.mapOrAccumulate
import arrow.core.nel
import arrow.core.right
import jakarta.inject.Named
import org.springframework.data.repository.findByIdOrNull
import tcla.contexts.tcla.core.application.failures.Failure
import tcla.contexts.tcla.core.domain.assessment.AssessmentFilterKey
import tcla.contexts.tcla.core.domain.assessment.AssessmentRepository
import tcla.contexts.tcla.core.domain.assessment.model.Assessment
import tcla.contexts.tcla.core.domain.assessment.model.AssessmentId
import tcla.contexts.tcla.core.domain.assessment.model.ResultsShareableToken
import tcla.contexts.tcla.core.domain.assessment.model.Step
import tcla.contexts.tcla.core.domain.team.model.TeamId
import tcla.contexts.tcla.database.springdatajpa.questionnaire.JpaQuestionnaire
import tcla.contexts.tcla.database.springdatajpa.questionnaire.JpaQuestionnaireRepository
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeam
import tcla.contexts.tcla.database.springdatajpa.team.JpaTeamRepository
import tcla.contexts.tcla.database.springdatajpa.toTimestamp
import tcla.libraries.search.Filter
import tcla.libraries.search.ManyValuesFilter
import tcla.libraries.search.OneValueFilter
import tcla.libraries.search.Operator
import java.time.Instant

@Named
class SqlAssessmentRepository(
    private val jpaAssessmentRepository: JpaAssessmentRepository,
    private val jpaQuestionnaireRepository: JpaQuestionnaireRepository,
    private val jpaTeamRepository: JpaTeamRepository
) : AssessmentRepository {
    override fun exists(id: AssessmentId): Either<NonEmptyList<Failure>, Boolean> =
        Either.catch { jpaAssessmentRepository.existsById(id.uuid) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { exists -> exists.right() }

    override fun existsByResultsShareableToken(resultsShareableToken: ResultsShareableToken): Either<NonEmptyList<Failure>, Boolean> =
        Either.catch { jpaAssessmentRepository.existsByResultsShareableToken(resultsShareableToken.token) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { exists -> exists.right() }

    override fun findByResultsShareableToken(resultsShareableToken: ResultsShareableToken): Either<NonEmptyList<Failure>, Assessment> =
        Either.catch { jpaAssessmentRepository.findByResultsShareableToken(resultsShareableToken.token) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaAssessment: JpaAssessment -> toDomain(jpaAssessment) }

    override fun find(id: AssessmentId): Either<NonEmptyList<Failure>, Assessment> =
        Either.catch { jpaAssessmentRepository.findByIdOrNull(id.uuid) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaAssessment: JpaAssessment? -> toDomain(jpaAssessment) }

    private fun toDomain(jpaAssessment: JpaAssessment?): Either<NonEmptyList<Failure>, Assessment> =
        when (jpaAssessment) {
            null -> Failure.EntityNotFound.Assessment.nel().left()
            else -> jpaAssessment.toDomain()
        }

    override fun save(assessment: Assessment): Either<NonEmptyList<Failure>, Assessment> =
        Either.catch { jpaTeamRepository.findByIdOrNull(assessment.teamId.uuid) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaTeam: JpaTeam? ->
                when (jpaTeam) {
                    null -> Failure.EntityNotFound.Team.nel().left()
                    else -> when (val questionnaireId = assessment.questionnaireId) {
                        null -> assessment.saveJpaAssessment(jpaQuestionnaire = null, jpaTeam)
                        else -> Either.catch { jpaQuestionnaireRepository.findByIdOrNull(questionnaireId.uuid) }
                            .mapLeft { Failure.DatabaseException(it).nel() }
                            .flatMap { jpaQuestionnaire: JpaQuestionnaire? ->
                                when (jpaQuestionnaire) {
                                    null -> Failure.EntityNotFound.Survey.nel().left()
                                    else -> assessment.saveJpaAssessment(jpaQuestionnaire, jpaTeam)
                                }

                            }
                    }
                }
            }

    override fun search(filter: Filter<AssessmentFilterKey>?): Either<NonEmptyList<Failure>, List<Assessment>> =
        findJpaAssessments(filter)
            .flatMap { jpaAssessments: List<JpaAssessment> ->
                jpaAssessments.mapOrAccumulate { jpaAssessment -> jpaAssessment.toDomain().bindNel() }
            }

    override fun searchByCurrentStepAndQuestionnaire_StartDateBefore(
        step: Step,
        instant: Instant
    ): Either<NonEmptyList<Failure>, List<Assessment>> =
        instant.toTimestamp()
            .mapLeft { failure -> failure.nel() }
            .flatMap { timestamp ->
                Either.catch {
                    jpaAssessmentRepository.findAllByCurrentStepAndQuestionnaire_StartDateBefore(
                        step = step.toJpa(),
                        timestamp = timestamp
                    )
                }.mapLeft { Failure.DatabaseException(it).nel() }
            }.flatMap { jpaAssessments ->
                jpaAssessments.mapOrAccumulate { jpaAssessment ->
                    jpaAssessment.toDomain().bindNel()
                }
            }

    override fun searchByCurrentStepAndQuestionnaire_EndDateBefore(
        step: Step,
        instant: Instant
    ): Either<NonEmptyList<Failure>, List<Assessment>> =
        instant.toTimestamp()
            .mapLeft { failure -> failure.nel() }
            .flatMap { timestamp ->
                Either.catch {
                    jpaAssessmentRepository.findAllByCurrentStepAndQuestionnaire_EndDateBefore(
                        step = step.toJpa(),
                        timestamp = timestamp
                    )
                }.mapLeft { Failure.DatabaseException(it).nel() }
            }.flatMap { jpaAssessments -> jpaAssessments.mapOrAccumulate { it.toDomain().bindNel() } }

    override fun searchByCurrentStep(
        step: Step
    ): Either<NonEmptyList<Failure>, List<Assessment>> =
        Either.catch {
            jpaAssessmentRepository.findAllByCurrentStep(
                step = step.toJpa()
            )
        }.mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaAssessments -> jpaAssessments.mapOrAccumulate { it.toDomain().bindNel() } }

    override fun searchByTeam_IdAndCurrentStepIsIn(
        teamId: TeamId,
        steps: Set<Step>
    ): Either<NonEmptyList<Failure>, List<Assessment>> =
        Either.catch {
            jpaAssessmentRepository.findAllByTeam_IdAndCurrentStepIsIn(
                teamId.uuid,
                steps.map { it.toJpa() }.toSet()
            )
        }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaAssessments -> jpaAssessments.mapOrAccumulate { jpaAssessment -> jpaAssessment.toDomain().bindNel() } }

    private fun findJpaAssessments(filter: Filter<AssessmentFilterKey>?): Either<NonEmptyList<Failure>, List<JpaAssessment>> =
        when (filter) {
            null -> Either.catch { jpaAssessmentRepository.findAll() }
                .mapLeft { throwable -> Failure.DatabaseException(exception = throwable).nel() }

            else -> when (filter) {
                is ManyValuesFilter<AssessmentFilterKey, *> -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()

                is OneValueFilter<AssessmentFilterKey, *> -> when (filter.key) {
                    AssessmentFilterKey.TEAM -> when (val value = filter.value) {
                        is TeamId -> when (filter.operator) {
                            Operator.BinaryOperator.Equal -> Either.catch {
                                jpaAssessmentRepository.findAllByTeam_Id(value.uuid)
                            }.mapLeft { throwable -> Failure.DatabaseException(throwable).nel() }

                            Operator.NaryOperator.In -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                        }

                        else -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                    }

                    AssessmentFilterKey.OWNER -> when (val value = filter.value) {
                        is String -> when (filter.operator) {
                            Operator.BinaryOperator.Equal -> Either.catch {
                                jpaAssessmentRepository.findAllByTeam_OwnerId(value)
                            }.mapLeft { throwable -> Failure.DatabaseException(throwable).nel() }

                            Operator.NaryOperator.In -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                        }

                        else -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                    }

                    AssessmentFilterKey.CURRENT_STEP -> when (val value = filter.value) {
                        is Step -> when (filter.operator) {
                            Operator.BinaryOperator.Equal -> Either.catch {
                                jpaAssessmentRepository.findAllByCurrentStep(value.toJpa())
                            }.mapLeft { throwable -> Failure.DatabaseException(throwable).nel() }

                            Operator.NaryOperator.In -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                        }

                        else -> Failure.UnsupportedDatabaseFilter.Assessment.nel().left()
                    }
                }
            }
        }

    private fun Assessment.saveJpaAssessment(
        jpaQuestionnaire: JpaQuestionnaire?,
        jpaTeam: JpaTeam
    ): Either<NonEmptyList<Failure>, Assessment> =
        Either.catch { jpaAssessmentRepository.save(toJpa(jpaQuestionnaire = jpaQuestionnaire, jpaTeam = jpaTeam)) }
            .mapLeft { Failure.DatabaseException(it).nel() }
            .flatMap { jpaAssessment -> jpaAssessment.toDomain() }
}
