package tcla.libraries.transactional

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.transaction.TransactionDefinition


enum class IsolationLevel {
    DEFAULT, READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE;
}

enum class PropagationBehavior {
    REQUIRED, SUPPORTS, MANDATORY, REQUIRES_NEW, NOT_SUPPORTED, NEVER, NESTED;
}

const val TIMEOUT_DEFAULT: Int = TransactionDefinition.TIMEOUT_DEFAULT

interface TransactionExecutor {
    fun <R : Any> transactional(
        isolationLevel: IsolationLevel = IsolationLevel.DEFAULT,
        timeout: Int = TIMEOUT_DEFAULT,
        isReadOnly: Boolean = false,
        propagationBehavior: PropagationBehavior = PropagationBehavior.REQUIRED,
        function: () -> R
    ): R
}

@Component
@Primary
class SpringTransactionExecutor : TransactionExecutor {


    override fun <R : Any> transactional(
        isolationLevel: IsolationLevel,
        timeout: Int,
        isReadOnly: Boolean,
        propagationBehavior: PropagationBehavior,
        function: () -> R
    ): R = transactional(
        isolationLevel = isolationLevel,
        timeout = timeout,
        isReadOnly = isReadOnly,
        propagationBehavior = propagationBehavior,
        enabled = true,
        function = function
    )
}


@Component
class FakeTransactionExecutor : TransactionExecutor {
    override fun <R : Any> transactional(
        isolationLevel: IsolationLevel,
        timeout: Int,
        isReadOnly: Boolean,
        propagationBehavior: PropagationBehavior,
        function: () -> R
    ): R = transactional(
        isolationLevel = isolationLevel,
        timeout = timeout,
        isReadOnly = isReadOnly,
        propagationBehavior = propagationBehavior,
        enabled = false,
        function = function
    )
}

fun <R : Any> transactional(
    isolationLevel: IsolationLevel = IsolationLevel.DEFAULT,
    timeout: Int = TIMEOUT_DEFAULT,
    isReadOnly: Boolean = false,
    propagationBehavior: PropagationBehavior = PropagationBehavior.REQUIRED,
    enabled: Boolean = true,
    function: () -> R
): R = TODO("Code not present in this demo")
