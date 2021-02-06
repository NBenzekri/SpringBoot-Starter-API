package com.nbenz.sbootstarter.configuration.swagger;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebSecurity
public class SwaggerConfig extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_LIST = { //
    "/v2/api-docs", //
    "/configuration/ui", //
    "/swagger-resources", //
    "/configuration/security", //
    "/swagger-ui.html", //
    "/webjars/**", //
    "/h2-console**", //
  };

  private static final String[] ACEESS_LIST = { //
    "/h2-console**", //
  };

  @Bean
  public Docket postsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("public-api")
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.nbenz"))
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("NouriBenz Spring Boot API Starter")
      .description("JavaInUse API reference for developers")
      .version("1.0")
      .build();
  }

  //   @Autowired
  //   public void configureGlobal(AuthenticationManagerBuilder auth)
  //     throws Exception {
  //     auth.inMemoryAuthentication().withUser("user").password("password");
  //   }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
      .withUser("user")
      .password(passwordEncoder().encode("password"))
      .roles("USER")
      .and()
      .withUser("admin")
      .password(passwordEncoder().encode("admin"))
      .roles("USER", "ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(AUTH_LIST)
    //   .antMatchers("/h2-console/**").permitAll()
      .authenticated()
      .and()
      .httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
