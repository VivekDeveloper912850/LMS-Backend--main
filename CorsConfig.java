 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
 @Configuration
 public class CorsConfig {
     @Bean
     public WebMvcConfigurer corsConfigurer() {
         return new WebMvcConfigurer() {
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                         .allowedOrigins("https://library-manageme-git-5ba4d7-vivekkumar912850-gmailcoms-projects.vercel.app")
                         .allowedMethods("GET", "POST", "PUT", "DELETE")
                         .allowedHeaders("*");
             }
         };
     }
 }
 
