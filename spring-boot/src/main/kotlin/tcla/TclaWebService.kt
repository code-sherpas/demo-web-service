package tcla

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TclaWebService

fun main(args: Array<String>) {
    runApplication<TclaWebService>(*args)
}
