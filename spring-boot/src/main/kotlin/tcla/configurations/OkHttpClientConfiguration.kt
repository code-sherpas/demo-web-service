package tcla.configurations

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OkHttpClientConfiguration {
    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient()
}
