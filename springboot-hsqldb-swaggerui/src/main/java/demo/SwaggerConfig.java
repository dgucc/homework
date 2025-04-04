package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

   @Bean
   public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(PathSelectors.any())
            .apis(RequestHandlerSelectors.basePackage("demo"))
            .build()
            .apiInfo(new ApiInfoBuilder().title("Calcisoc VA API").version("1.0").build());
   }
}