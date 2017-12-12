package br.edu.cesar.philippe.contoleacesso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return CorsMappings.corsConfigurer();
    }

    private static class CorsMappings {
        private static WebMvcConfigurer corsConfigurer() {
            return (WebMvcConfigurerAdapter r) -> r.addCorsMappings(CorsRegistry"/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            };
          
        }
    }

}
