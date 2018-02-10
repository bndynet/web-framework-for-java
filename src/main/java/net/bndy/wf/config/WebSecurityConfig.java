/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.config;

import com.google.common.collect.ImmutableList;
import net.bndy.lib.StringHelper;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.core.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // if Spring MVC is on classpath and no CorsConfigurationSource is provided,
            // Spring Security will use CORS configuration provided to Spring MVC
            .cors().and()
            .csrf().disable()
            .headers().frameOptions().sameOrigin();

        http.
            // Purpose: return 401 if non-authorized api access
            // Actually Spring already configures default AuthenticationEntryPoint - LoginUrlAuthenticationEntryPoint
            // This one is REST-specific addition to default one, that is based on PathRequest
            exceptionHandling()
            .defaultAuthenticationEntryPointFor(this.restAuthenticationEntryPoint(), new AntPathRequestMatcher("/api/**"))

            .and()
            .authorizeRequests()
            .antMatchers("/webjars/**", "/static/**", "/test/**", "/v2/api-docs",
                "/docs/**", "/error",
                "/*",
                "/oauth/authorize",
                // The following paths should be protected
//                "/oauth/token",
//                "/oauth/confirm_access",
//                "/oauth/check_token",
//                "/oauth/error",
//                "/oauth/token_key",
                "/api/core/users/login",
                "/api/v1/core/users/login",
                "/sso/login*", "/sso/signup").permitAll()
//            .antMatchers("/admin/**").hasAnyAuthority([rolename])
            .antMatchers("/admin/**","/api/**").authenticated()
            .anyRequest().permitAll();

        http
            .formLogin()
            .loginPage("/sso/login")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/sso/logout")
            .logoutSuccessUrl("/sso/login?logout")
            .deleteCookies("JSESSIONID")
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Solution 1
        // auth.inMemoryAuthentication()
        // .withUser("admin").password("123456").roles("ADMIN");

        // Solution 2
        // auth.jdbcAuthentication().dataSource(this.dataSource)
        // .usersByUsernameQuery("SELECT username, password, enabled FROM
        // app_user WHERE username=?")
        // .authoritiesByUsernameQuery("SELECT username, role FROM app_user_role
        // WHERE username=?");

        // Solution 3
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
    }

    /**
     * CORS:
     * <p>
     * Do not do any of below, which are the wrong way to attempt solving the ajax problem:
     * - http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
     * - web.ignoring().antMatchers(HttpMethod.OPTIONS)
     * <p>
     * Global CORS configuration
     * https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
     * https://docs.spring.io/spring-security/site/docs/current/reference/html/cors.html
     * <p>
     * Solution 1
     * add CrossOrigin annotation to Controller class or methods
     * <p>
     * Solution 2
     * override addCorsMappings(CorsRegistry registry) method of WebMvcConfigurerAdapter class
     * <p>
     * <p>
     * The follow method will override CORS Configuration provided by Spring MVC.
     */
    @Bean
    public FilterRegistrationBean initCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        config.setAllowCredentials(true);

        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        config.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

        config.addAllowedMethod("*");

        String origins = this.applicationConfig.getAllowedOrigins();
        if (origins != null && !"".equals(origins)) {
            config.setAllowedOrigins(Arrays.asList(StringHelper.splitWithoutWhitespace(origins, ",")));
        }
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ApplicationContext.language("error.unauthorizedAccess"));
            }
        };
    }
}
