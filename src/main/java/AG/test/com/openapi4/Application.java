package AG.test.com.openapi4;

import AG.test.com.openapi4.filters.BearerAuthFilter;
import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.openapitools", "AG.test.com.openapi4.api" , "org.openapitools.configuration", "AG.test.com.openapi4", "AG.test.com.openapi4.filters"})
public class Application
{

    public static void main(String[] args) {
        SpringApplication.run(org.openapitools.OpenApiGeneratorApplication.class, args);
    }

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }


    @Bean
    public FilterRegistrationBean<BearerAuthFilter> myFilter()
    {
        FilterRegistrationBean<BearerAuthFilter> frb = new FilterRegistrationBean<>();

        frb.setFilter(new BearerAuthFilter());
        frb.addUrlPatterns("/api/myres");
        frb.setOrder(1);

        return frb;
    }

}
