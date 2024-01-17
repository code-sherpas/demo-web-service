package tcla


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HomeController{

    @GetMapping("/", produces = ["application/json"])
    fun execute(): ResponseEntity<String> {
        return ResponseEntity.ok("""
            {
                "appName": "demo"
            }
        """.trimIndent())
    }
}
