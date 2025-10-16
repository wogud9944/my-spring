package c.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(
            "http://localhost:5173",
            "https://<YOUR_VERCEL_DOMAIN>.vercel.app" // 여기 교체!
        )
        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
        .allowedHeaders("*");
}
}
