package net.bndy.wf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.bndy.wf.interceptors.*;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	AuthenticationInterceptor authenticationInterceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/doc/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/doc/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	//registry.addInterceptor(authenticationInterceptor);
		super.addInterceptors(registry);
	}	
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/doc/api", "/doc/swagger-ui.html");
		registry.addRedirectViewController("/doc/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/doc/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/doc/swagger-resources/configuration/security","/swagger-resources/configuration/security");
        registry.addRedirectViewController("/doc/swagger-resources", "/swagger-resources");
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

}