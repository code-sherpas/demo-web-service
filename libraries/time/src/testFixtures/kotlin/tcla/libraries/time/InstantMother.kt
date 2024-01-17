package tcla.libraries.time

import java.time.Duration
import java.time.Instant

object InstantMother {
    fun nowMinusOneDay(): Instant = Instant.now().minus(Duration.ofDays(1))
    fun nowPlusOneDay(): Instant = Instant.now().plus(Duration.ofDays(1))
    fun nowMinusOneDayAsIso() = nowMinusOneDay().toString()
    fun nowPlusOneDayAsIso() = nowPlusOneDay().toString()
}
