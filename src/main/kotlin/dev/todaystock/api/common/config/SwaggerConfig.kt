
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val jwt = "JWT"
        val components = Components().addSecuritySchemes(
            jwt, SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat(jwt)
        )
        return OpenAPI()
            .components(Components())
            .info(apiInfo())
            .addSecurityItem(SecurityRequirement().addList(jwt))
            .components(components)
    }

    private fun apiInfo(): Info {
        return Info()
            .title("TodayStock API 명세")
            .description("TodayStock API 명세 자료입니다.")
            .version("1.0.0")
    }
}