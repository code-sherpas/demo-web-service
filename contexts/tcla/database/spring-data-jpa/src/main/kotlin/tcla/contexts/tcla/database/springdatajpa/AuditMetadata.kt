package tcla.contexts.tcla.database.springdatajpa

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp


@Embeddable
data class AuditMetadata(
    @LastModifiedDate
    @Column(nullable = false)
    var lastUpdateAt: Timestamp? = null,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var creationAt: Timestamp? = null
)
