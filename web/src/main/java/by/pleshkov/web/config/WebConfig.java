package by.pleshkov.web.config;

import by.pleshkov.config.ServiceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static by.pleshkov.web.util.PagesUtil.PREFIX;
import static by.pleshkov.web.util.PagesUtil.SUFFIX;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan("by.pleshkov.web")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp(PREFIX, SUFFIX);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
