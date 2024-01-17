package tcla.libraries.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass



fun Any.logError(message: String) = getLogger(this::class).error(message)
private fun getLogger(clazz: KClass<out Any>): Logger = LoggerFactory.getLogger(clazz.java)
