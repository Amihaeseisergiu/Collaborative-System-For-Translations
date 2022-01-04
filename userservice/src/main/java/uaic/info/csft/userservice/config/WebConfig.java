package uaic.info.csft.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Set the domain name that allows cross-domain requests
                .allowedOriginPatterns("*")
                // whether to allow certificates (cookies)
                .allowCredentials(true)
                // set the allowed methods
                .allowedMethods("*")
                // Allowed time across domains
                .maxAge(3600);
    }
}