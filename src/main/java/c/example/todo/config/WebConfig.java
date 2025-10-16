// src/main/java/c/example/todo/config/WebConfig.java
package c.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        // 로컬 + Vercel 배포 도메인 허용
        .allowedOriginPatterns(
            "http://localhost:5173",
            "https://*.vercel.app"
        )
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .exposedHeaders("*")
        .allowCredentials(false)
        .maxAge(3600);
  }
}
