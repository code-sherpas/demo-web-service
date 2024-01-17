package tcla.contexts.authentication.core

import java.util.UUID

object RequestInfo {
    private val requesterId: ThreadLocal<String?> = ThreadLocal.withInitial { null }
    private val requestId: ThreadLocal<UUID> = ThreadLocal.withInitial { UUID.randomUUID() }
    fun setRequesterId(requesterId: String?) = RequestInfo.requesterId.set(requesterId)
    fun getRequesterId(): String? = requesterId.get()

    fun setRequestId(requestId: UUID) = RequestInfo.requestId.set(requestId)
    fun getRequestId(): UUID = requestId.get()
}
